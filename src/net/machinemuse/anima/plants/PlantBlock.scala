package net.machinemuse.anima.block.plants

import net.minecraft.block.{Block, BlockCrops}
import net.minecraft.item.{Item, ItemBlock}
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.util.Icon
import net.machinemuse.anima.AnimaTab
import java.util.Random
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import java.util.logging.Logger


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:34 AM, 6/18/13
 */

object PlantBlock {
  var id = 0
}

class PlantBlocks(id: Int) extends BlockCrops(id) {
  PlantBlock.id = id
  setTickRandomly(true)
  val border: Float = 0.625F
  setBlockBounds(border, 0.0F, border, 1.0F - border, 1.0F - 2 * border, 1.0F - border)
  setCreativeTab(AnimaTab)
  setHardness(0.5F)
  setStepSound(Block.soundGrassFootstep)
  disableStats()

  new PlantItemBlock(id)
  System.err.println("PLANT BLOCK REGISTERE")

  def downcast(te: TileEntity) = te match {
    case e: PlantTileEntity => Some(e)
    case _ => None
  }

  def getPlantTE(world: World, x: Int, y: Int, z: Int):Option[PlantTileEntity] = downcast(world.getBlockTileEntity(x, y, z))

  override def fertilize(world: World, x: Int, y: Int, z: Int) {
    getPlantTE(world, x, y, z) map {
      e => e.fertilize(); e.invalidate()
    }
  }

  override def hasTileEntity(metadata: Int): Boolean = true

  override def createTileEntity(world: World, metadata: Int): TileEntity = new PlantTileEntity()



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
  var plantType:String = ""

  def fertilize() {

  }
}

class PlantItemBlock(id: Int) extends ItemBlock(id) {
  setHasSubtypes(true)
  setMaxDamage(0)
  setCreativeTab(AnimaTab)


//  @SideOnly(Side.CLIENT)
//  override def getIconFromDamage(damage: Int): Icon = {
//    PlantPart.subtypes(damage).registeredIcon
//  }
}

