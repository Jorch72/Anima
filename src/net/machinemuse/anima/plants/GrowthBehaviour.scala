package net.machinemuse.anima.plants

import net.machinemuse.anima.block.plants.{PlantBlock, PlantTileEntity, PlantPart}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:27 PM, 6/18/13
 */
abstract class GrowthBehaviour {
  def grow(e: PlantTileEntity)
}

case class GrowInPlace(replacement: PlantPart) extends GrowthBehaviour {
  def grow(e: PlantTileEntity) {
    e.plantPart = replacement.name
  }
}

case class GrowUp(extension: PlantPart) extends GrowthBehaviour {
  def grow(e: PlantTileEntity) {
    if(e.worldObj.isAirBlock(e.xCoord, e.yCoord+1, e.zCoord)) {
      val newte = new PlantTileEntity
      newte.plantPart = extension.name
      e.worldObj.setBlock(e.xCoord, e.yCoord+1, e.zCoord, PlantBlock.block.blockID)
      e.worldObj.setBlockTileEntity(e.xCoord, e.yCoord+1, e.zCoord, newte)
    }
  }
}

case class NoGrowth() extends GrowthBehaviour {
  def grow(e: PlantTileEntity) {}
}