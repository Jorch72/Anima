package net.machinemuse.anima.block

import cpw.mods.fml.client.registry.{ISimpleBlockRenderingHandler, RenderingRegistry}
import cpw.mods.fml.common.registry.GameRegistry
import net.machinemuse.anima.AnimaTab
import net.machinemuse.anima.entity.{AnimaEntityHarvestSprite, AnimaEntitySprite}
import net.machinemuse.anima.item.Incense
import net.machinemuse.numina.random.MuseRandom
import net.machinemuse.numina.render.{MuseTESR, ParticleDictionary}
import net.machinemuse.numina.scala.OptionCast
import net.machinemuse.numina.tileentity.MuseTileEntity
import net.minecraft.block.material.Material
import net.minecraft.block.{Block, BlockContainer}
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.command.IEntitySelector
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{ResourceLocation, AxisAlignedBB}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.client.model.AdvancedModelLoader
import net.minecraftforge.client.model.obj.WavefrontObject
import org.lwjgl.opengl.GL11._

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:58 AM, 11/13/13
 */
object BlockIncenseBurner extends BlockContainer(Material.clay) {

  setBlockName("incenseburner")

  setLightOpacity(0)

  GameRegistry.registerTileEntity(classOf[TileEntityIncenseBurner], "incenseburner")

  setCreativeTab(AnimaTab)

  override def createNewTileEntity(world: World, idk: Int): TileEntity = new TileEntityIncenseBurner

  override def renderAsNormalBlock: Boolean = false

  override def isOpaqueCube: Boolean = false

  override def getRenderType: Int = IncenseBurnerRenderer.getRenderId


  /**
   * Called upon block activation (right click on the block.)
   */
  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, sideHit: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    OptionCast[TileEntityIncenseBurner](world.getTileEntity(x, y, z)) map {
      te =>
        val stack = player.getCurrentEquippedItem
        if (!te.hasIncense && stack != null && (stack.getItem eq Incense))
          te.incense = Some(stack.splitStack(1))
        else if (!te.isBurning) {
          te.isBurning = true
        } else if (te.hasIncense) {
          val stack = te.incense.get
          te.incense = None
          player.dropPlayerItemWithRandomChoice(stack, false) // TODO: Find out what the flag does
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
          tryToSpawn(new AnimaEntityHarvestSprite(worldObj, xCoord, yCoord, zCoord).setSpot(between(area.minX, area.maxX), between(yCoord, (yCoord + area.maxY) / 2.0), between(area.minZ, area.maxZ)), stack)
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

object IncenseBurnerRenderer extends MuseTESR with ISimpleBlockRenderingHandler {
  val incensestick = AdvancedModelLoader.loadModel(new ResourceLocation("anima:models/incenseStick.obj")).asInstanceOf[WavefrontObject]
  val incenseholder = AdvancedModelLoader.loadModel(new ResourceLocation("anima:models/incenseHolderAndCore.obj")).asInstanceOf[WavefrontObject]
  val renderID = RenderingRegistry.getNextAvailableRenderId

  def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks): Unit = {
    this.bindTextureByName("anima:models/incense.png")
    glPushMatrix()
    val scale: Double = 0.0625
    glScaled(scale, scale, scale)
    incenseholder.renderAll()
    glPopMatrix()
  }

  def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = true

  def getRenderId: Int = renderID

  override def renderAt(tileentity: TileEntity, x: Double, y: Double, z: Double, partialTickTime: Float): Unit = {
    this.bindTextureByName("anima:models/incense.png")
    glPushMatrix()
    glTranslated(x + 0.5, y, z + 0.5)
    val scale: Double = 0.0625
    glScaled(scale, scale, scale)
    incenseholder.renderAll()
    if (tileentity.asInstanceOf[TileEntityIncenseBurner].hasIncense) {
      val stack = tileentity.asInstanceOf[TileEntityIncenseBurner].incense.get
      val length: Double = 1.0D - (stack.getItemDamage.toDouble / stack.getMaxDamage.toDouble)
      glTranslated(4.046D, 1.688D, 0.092D)
      glRotated(75, 0, 0, 1)
      glScaled(1, length, 1)
      incensestick.renderAll()
    }
    glPopMatrix()
  }

  override def shouldRender3DInInventory(modelId: Int): Boolean = true
}