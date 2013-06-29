package net.machinemuse.anima.block

import net.machinemuse.anima.block.plants.{PlantItemBlock, PlantBlock}
import cpw.mods.fml.common.registry.GameRegistry

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:33 AM, 6/18/13
 */

object AnimaBlock {
  var plantBlock: PlantBlock = null

  def init(id: Int) {
    plantBlock = new PlantBlock(id)
    GameRegistry.registerBlock(plantBlock, classOf[PlantItemBlock], plantBlock.getUnlocalizedName)
  }
}

//
//class AnimaBlock(id: Int, mat: Material) extends Block(id, mat) {
//
//}
