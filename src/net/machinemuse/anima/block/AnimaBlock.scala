package net.machinemuse.anima.block

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.machinemuse.anima.block.plants.PlantBlocks

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:33 AM, 6/18/13
 */

object AnimaBlock {
  var plantBlock: PlantBlocks = null

  def init(id:Int) {
    plantBlock = new PlantBlocks(id)
  }
}

class AnimaBlock(id: Int, mat: Material) extends Block(id, mat) {

}
