package net.machinemuse.anima.block.plants

import net.minecraft.block.{Block, BlockCrops}
import net.minecraft.item.{ItemStack, ItemBlock}
import net.machinemuse.anima.AnimaTab
import java.util.Random
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import net.minecraft.creativetab.CreativeTabs
import java.util
import net.minecraft.util.Icon
import net.minecraft.client.renderer.texture.IconRegister
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import net.machinemuse.anima.block.AnimaTileEntity


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:34 AM, 6/18/13
 */

object PlantBlock {
  var block: PlantBlock = null
  var item: PlantItemBlock = null
}

class PlantBlock(id: Int) extends BlockCrops(id) {
  PlantBlock.block = this

  setTickRandomly(true)
  setCreativeTab(AnimaTab)
  setHardness(0.5F)
  setStepSound(Block.soundGrassFootstep)
  setUnlocalizedName("anima.plantBlockUnknown")
  disableStats()

  val border: Float = 0.0625F
  setBlockBounds(border, 0.0F, border, 1.0F - border, 1.0F - 2 * border, 1.0F - border)

  override def fertilize(world: World, x: Int, y: Int, z: Int) {
    PlantPartRegistry.getPlantTileEntity(world, x, y, z) map {
      e =>
        e.fertilize()
        e.updateContainingBlockInfo()
    }
  }

  override def getRenderBlockPass = 1

  override def getRenderType = PlantRenderer.getRenderId

  override def isOpaqueCube = false

  override def hasTileEntity(metadata: Int): Boolean = true

  override def createTileEntity(world: World, metadata: Int): TileEntity = new PlantTileEntity()

  @SideOnly(Side.CLIENT) override def getIcon(par1: Int, par2: Int): Icon = WoadLeavesFull.render.icon


  override def registerIcons(reg: IconRegister) {
    PlantPartRegistry.elems foreach {
      part => part.render.registerIcon(reg)
    }
  }

  override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, entity: EntityLiving, stack: ItemStack) {
    PlantPartRegistry.getPlantPart(stack).map {
      part => PlantPartRegistry.getPlantTileEntity(world, x, y, z).map {
        te => te.plantPart = part.name
      }
    }
  }

  override def getSubBlocks(blockID: Int, tab: CreativeTabs, itemlist: util.List[_]) {
    val list = itemlist.asInstanceOf[util.List[ItemStack]]
    PlantPartRegistry.elems foreach {
      part =>
        list.add(part.itemStack)
    }
  }

  /**
   * Ticks the block if it's been scheduled
   */
  override def updateTick(world: World, x: Int, y: Int, z: Int, random: Random) {
    PlantPartRegistry.getPlantTileEntity(world, x, y, z) map {
      e =>
        e.fertilize()
        e.updateContainingBlockInfo()
    }
  }
}

class PlantTileEntity extends AnimaTileEntity {
  var growthProgress: Byte = 0
  var plantPart: String = ""

  def fertilize() {
    PlantPartRegistry.get(plantPart) map {
      p =>
        p.growth.grow(this)
        updateContainingBlockInfo()
    }
  }

  override def canUpdate = false

  override def updateEntity() {
    growthProgress = (growthProgress + 1).toByte
    if (growthProgress > 0) {
      fertilize()
      growthProgress = 0
    }
  }

  override def writeToNBT(nbt: NBTTagCompound) {
    super.writeToNBT(nbt)
    nbt.setByte("g", growthProgress)
    nbt.setString("p", plantPart)
  }

  override def readFromNBT(nbt: NBTTagCompound) {
    super.readFromNBT(nbt)
    growthProgress = nbt.getByte("g")
    plantPart = nbt.getString("p")
  }
}

class PlantItemBlock(id: Int) extends ItemBlock(id) {
  PlantBlock.item = this
  setHasSubtypes(true)
  setMaxDamage(0)
  setCreativeTab(AnimaTab)
  setUnlocalizedName("anima.plantItemBlockDefault")


  //  override def addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: util.List[_], par4: Boolean) {
  //    par3List.asInstanceOf[util.List[String]].add(getUnlocalizedName(par1ItemStack))
  //  }

  override def getIcon(stack: ItemStack, pass: Int): Icon = {
    PlantPartRegistry.getPlantPart(stack) match {
      case Some(e) => e.render.icon
      case None => null
    }
  }

  //  @SideOnly(Side.CLIENT)
  //  override def getIconFromDamage(damage: Int): Icon = {
  //    PlantPart.subtypes(damage).registeredIcon
  //  }
  override def getUnlocalizedName(stack: ItemStack): String = {
    PlantPartRegistry.getPlantPart(stack) match {
      case Some(e) => e.name
      case None => "anima.plantItemBlockUnknown"
    }
  }

}

