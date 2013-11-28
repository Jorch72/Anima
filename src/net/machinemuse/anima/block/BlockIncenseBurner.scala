package net.machinemuse.anima.block

import net.minecraft.block.BlockContainer
import net.machinemuse.anima.item.{Incense, BlockIDManager}
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import net.minecraft.entity.player.EntityPlayer
import net.machinemuse.numina.scala.OptionCast
import cpw.mods.fml.common.registry.GameRegistry
import net.machinemuse.anima.AnimaTab
import net.machinemuse.numina.tileentity.MuseTileEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.AxisAlignedBB
import net.machinemuse.numina.random.MuseRandom
import net.machinemuse.numina.render.ParticleDictionary
import net.machinemuse.anima.entity.{AnimaEntitySprite, AnimaEntityHarvestSprite}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.command.IEntitySelector
import net.minecraft.entity.Entity
import net.machinemuse.numina.general.MuseLogger

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:58 AM, 11/13/13
 */
object BlockIncenseBurner extends BlockContainer(BlockIDManager.getID("incenseburner"), Material.clay) {

  setUnlocalizedName("incenseburner")

  GameRegistry.registerTileEntity(classOf[TileEntityIncenseBurner], "incenseburner")

  setCreativeTab(AnimaTab)

  override def createNewTileEntity(world: World): TileEntity = new TileEntityIncenseBurner

  /**
   * Called upon block activation (right click on the block.)
   */
  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, sideHit: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    OptionCast[TileEntityIncenseBurner](world.getBlockTileEntity(x, y, z)) map {
      te =>
        val stack = player.getCurrentEquippedItem
        if (!te.hasIncense && stack != null && (stack.getItem eq Incense))
          te.incense = Some(stack.splitStack(1))
        else if (!te.isBurning) {
          te.isBurning = true
        } else if (te.hasIncense) {
          val stack = te.incense.get
          te.incense = None
          player.dropPlayerItem(stack)
          te.isBurning = false
        }
    }
    true
  }

}

class TileEntityIncenseBurner extends MuseTileEntity {
  var incense: Option[ItemStack] = None
  var isBurning = false
  val radius = 16
  val area = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0)

  updateArea()

  def updateArea() {
    area.setBounds(
      xCoord - radius, yCoord - radius, zCoord - radius,
      xCoord + radius, yCoord + radius, zCoord + radius)
  }

  def hasIncense = {
    incense match {
      case Some(i) => i.getItem eq Incense
      case None => false
    }
  }

  override def updateEntity() {
    if (isBurning) {
      incense match {
        case None => isBurning = false
        case Some(stack) =>
          worldObj.spawnParticle(ParticleDictionary.smoke, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 0, 0.01, 0)
          attemptSpawnSprite()
          if (stack.attemptDamageItem(1, MuseRandom)) {
            incense = None
          }
      }
    }
  }

  def attemptSpawnSprite() {
    if (!worldObj.isRemote && MuseRandom.nextDouble < 0.1) {
      updateArea()
      incense map {
        stack =>
          tryToSpawn(new AnimaEntityHarvestSprite(worldObj, xCoord, yCoord, zCoord).setSpot(between(area.minX, area.maxX), between(yCoord, (yCoord+area.maxY)/2.0), between(area.minZ, area.maxZ)), stack)
        //          tryToSpawn(new AnimaEntityHarvestSprite(worldObj), stack)
      }
    }
  }

  val spriteSelector = new IEntitySelector {
    def isEntityApplicable(entity: Entity): Boolean = entity.isInstanceOf[AnimaEntitySprite]
  }

  def tryToSpawn(entity: AnimaEntitySprite, stack: ItemStack) {
    if (entity.getCanSpawnHere && entity.isValidIncense(stack)) {
      val nearbySprites = worldObj.getEntitiesWithinAABBExcludingEntity(entity, area, spriteSelector)
      if (nearbySprites.size() < 16) {
        worldObj.spawnEntityInWorld(entity)
      }
    }
  }

  private def between(min: Double, max: Double) = {
    min + (max - min) * MuseRandom.nextDouble
  }

  override def writeToNBT(nbt: NBTTagCompound) {
    super.writeToNBT(nbt)
    incense map {
      stack =>
        writeItemStack(nbt, "incense", stack)
    }
    if (isBurning) {
      nbt.setBoolean("burning", true)
    }
  }

  override def readFromNBT(nbt: NBTTagCompound) {
    super.readFromNBT(nbt)
    getItemStack(nbt, "incense") map (is => incense = Some(is))
    getBoolean(nbt, "burning") map (b => isBurning = b)
  }

  override def canUpdate: Boolean = true
}