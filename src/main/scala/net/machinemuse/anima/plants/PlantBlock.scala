package net.machinemuse.anima.plants

import net.minecraft.block.{Block, BlockCrops}
import net.minecraft.item.{Item, ItemStack, ItemBlock}
import net.machinemuse.anima.AnimaTab
import java.util.Random
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import net.minecraft.creativetab.CreativeTabs
import java.util
import net.minecraft.util.IIcon
import net.minecraft.client.renderer.texture.IIconRegister
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.entity.EntityLivingBase
import net.machinemuse.anima.plants.parts.WoadLeavesFull


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:34 AM, 6/18/13
 */

object PlantBlock {
  var block: PlantBlock = null
  var item: PlantItemBlock = null
}

class PlantBlock extends BlockCrops {
  PlantBlock.block = this

  setTickRandomly(true)
  setCreativeTab(AnimaTab)
  setHardness(0.5F)
  setStepSound(Block.soundTypeGrass)
  setBlockName("anima.plantBlockUnknown")
  disableStats()

  val border: Float = 0.0625F
  setBlockBounds(border, 0.0F, border, 1.0F - border, 1.0F - 2 * border, 1.0F - border)

  override def func_149863_m(world: World, x: Int, y: Int, z: Int) { // Guessing this is fertilize
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

  @SideOnly(Side.CLIENT) override def getIcon(par1: Int, par2: Int): IIcon = WoadLeavesFull.render.icon


  override def registerBlockIcons(reg: IIconRegister) {
    PlantPartRegistry.elems foreach {
      part => part.render.registerIcon(reg)
    }
  }

  override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, entity: EntityLivingBase, stack: ItemStack) {
    PlantPartRegistry.getPlantPart(stack).map {
      part => PlantPartRegistry.getPlantTileEntity(world, x, y, z).map {
        te => te.plantPart = part.name
      }
    }
  }

  override def getSubBlocks(block: Item, tab: CreativeTabs, itemlist: util.List[_]) {
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


class PlantItemBlock(block: Block) extends ItemBlock(block) {
  PlantBlock.item = this
  setHasSubtypes(true)
  setMaxDamage(0)
  setCreativeTab(AnimaTab)
  setUnlocalizedName("anima.plantItemBlockDefault")


  //  override def addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: util.List[_], z: Boolean) {
  //    par3List.asInstanceOf[util.List[String]].add(getUnlocalizedName(par1ItemStack))
  //  }

  override def getIcon(stack: ItemStack, pass: Int): IIcon = {
    PlantPartRegistry.getPlantPart(stack) match {
      case Some(e) => e.render.icon
      case None => null
    }
  }

  //  @SideOnly(Side.CLIENT)
  //  override def getIIconFromDamage(damage: Int): IIcon = {
  //    PlantPart.subtypes(damage).registeredIIcon
  //  }
  override def getUnlocalizedName(stack: ItemStack): String = {
    PlantPartRegistry.getPlantPart(stack) match {
      case Some(e) => "anima." + e.name
      case None => "anima.plantItemBlockUnknown"
    }
  }

}

