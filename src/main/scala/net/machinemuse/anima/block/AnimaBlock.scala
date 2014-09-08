package net.machinemuse.anima.block

import cpw.mods.fml.common.registry.GameRegistry
import net.machinemuse.anima.plants.{PlantItemBlock, PlantBlock, PlantTileEntity}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:33 AM, 6/18/13
 */

object AnimaBlock {
  var plantBlock: PlantBlock = null

  def init(id: Int) {
    plantBlock = new PlantBlock(id)
    GameRegistry.registerBlock(plantBlock, classOf[PlantItemBlock], plantBlock.getUnlocalizedName)
    GameRegistry.registerTileEntity(classOf[PlantTileEntity], plantBlock.getUnlocalizedName)

  }
}

//
//class AnimaBlock(id: Int, mat: Material) extends Block(id, mat) {
//
//}
