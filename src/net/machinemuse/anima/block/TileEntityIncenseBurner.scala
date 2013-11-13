package net.machinemuse.anima.block

import net.minecraft.tileentity.{TileEntityFurnace, TileEntity}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraft.item.ItemStack

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:59 AM, 11/13/13
 */
class TileEntityIncenseBurner extends TileEntity {
  var incense:Option[ItemStack] = None
  var isBurning = false

  def hasIncense = {
    incense match {
      case Some(i:ItemStack) => true
      case _ => false
    }
  }
}