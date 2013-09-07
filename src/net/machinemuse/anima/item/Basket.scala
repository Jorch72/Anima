package net.machinemuse.anima.item

import net.minecraft.item.ItemStack
import net.minecraft.util.Icon
import net.machinemuse.numina.item.ModeChangingItem
import net.minecraft.entity.player.EntityPlayer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:16 PM, 8/7/13
 */
object Basket extends AnimaSubItem with ModeChangingItem {
  def name = "basket"

  def iconFile = "basket"

  override def getActiveMode(stack: ItemStack): String = super.getActiveMode(stack)

  def getPrevModeIcon(stack: ItemStack): Icon = ???

  def getCurrentModeIcon(stack: ItemStack): Icon = ???

  def getNextModeIcon(stack: ItemStack): Icon = ???

  def cycleMode(stack: ItemStack, dmode: Int) {}
}
