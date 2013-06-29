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
import net.minecraft.entity.player.EntityPlayer


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

  val border: Float = 0.625F
  setBlockBounds(border, 0.0F, border, 1.0F - border, 1.0F - 2 * border, 1.0F - border)

  override def fertilize(world: World, x: Int, y: Int, z: Int) {
    PlantPartRegistry.getPlantTileEntity(world, x, y, z) map {
      e =>
        e.fertilize()
        e.invalidate()
    }
  }

  override def isOpaqueCube = false

  override def hasTileEntity(metadata: Int): Boolean = true

  override def createTileEntity(world: World, metadata: Int): TileEntity = new PlantTileEntity()


  override def registerIcons(reg: IconRegister) {
    PlantPartRegistry.elems foreach {
      part => part.render.registerIcon(reg)
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
    val te: TileEntity = world.getBlockTileEntity(x, y, z)
    te.updateEntity()
    te.invalidate()
  }
}

class PlantTileEntity extends TileEntity {
  var growthProgress: Byte = 0
  var plantPart: String = ""

  def fertilize() {
    PlantPartRegistry.get(plantPart) map {
      p =>
        p.growth.grow(this)
    }

  }
}

class PlantItemBlock(id: Int) extends ItemBlock(id) {
  PlantBlock.item = this
  setHasSubtypes(true)
  setMaxDamage(0)
  setCreativeTab(AnimaTab)
  setUnlocalizedName("anima.plantBlockUnknown")


  override def addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: util.List[_], par4: Boolean) {
    par3List.asInstanceOf[util.List[String]].add(getUnlocalizedName(par1ItemStack))
  }

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
      case None => "anima.plantBlockUnknown"
    }
  }

}

