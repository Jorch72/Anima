package net.machinemuse.anima.block

import net.minecraft.block.BlockContainer
import net.machinemuse.anima.item.BlockIDManager
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import net.minecraft.entity.player.EntityPlayer
import scala.Option
import net.machinemuse.numina.scala.OptionCast
import net.machinemuse.anima.item.Incense

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:58 AM, 11/13/13
 */
object BlockIncenseBurner extends BlockContainer(BlockIDManager.getID("blockIncenseBurner"), Material.clay) {

  override def createNewTileEntity(world: World): TileEntity = new TileEntityIncenseBurner

  /**
   * Called upon block activation (right click on the block.)
   */
  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, sideHit: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    for (
      stack <- Option(player.getCurrentEquippedItem)
//      item <- OptionCast[Incense](stack.getItem)
    ) {

    }

    true
  }

}
