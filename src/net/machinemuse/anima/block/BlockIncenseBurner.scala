package net.machinemuse.anima.block

import net.minecraft.block.BlockContainer
import net.machinemuse.anima.item.{Incense, BlockIDManager}
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import net.minecraft.entity.player.EntityPlayer
import net.machinemuse.numina.scala.OptionCast
import cpw.mods.fml.common.registry.GameRegistry

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:58 AM, 11/13/13
 */
object BlockIncenseBurner extends BlockContainer(BlockIDManager.getID("incenseburner"), Material.clay) {

  GameRegistry.registerTileEntity(classOf[TileEntityIncenseBurner], "incenseburner")

  override def createNewTileEntity(world: World): TileEntity = new TileEntityIncenseBurner

  /**
   * Called upon block activation (right click on the block.)
   */
  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, sideHit: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    OptionCast[TileEntityIncenseBurner](world.getBlockTileEntity(x, y, z)) map {
      te =>
        val stack = player.getCurrentEquippedItem
        if (!te.hasIncense && stack != null && (stack.getItem eq Incense))
          te.incense = Some(stack.splitStack(1))
        else if (!te.isBurning) {
          te.isBurning = true
        } else if (te.hasIncense) {
          val stack = te.incense.get
          te.incense = None
          player.dropPlayerItem(stack)
        }
    }
    true
  }

}
