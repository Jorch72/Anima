package net.machinemuse.anima.item

import net.minecraft.item.{ItemFood, Item, ItemStack}
import net.minecraft.util.Icon
import net.machinemuse.numina.item.{InventoriedItem, ModeChangingItem}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:16 PM, 8/7/13
 */
object Basket
  extends Item(12312)
  with ModeChangingItem
  with InventoriedItem {
  def name = "basket"

  def iconFile = "basket"

  override def getActiveMode(stack: ItemStack): String = super.getActiveMode(stack)

  def getPrevModeIcon(stack: ItemStack): Option[Icon] = {
    getSelectedSlot(stack) match {
      case 0 => Some(getContents(stack).last.getIconIndex)
      case x => Some(getContents(stack)(x - 1).getIconIndex)
    }
  }

  def getCurrentModeIcon(stack: ItemStack): Option[Icon] =
    Option(getContents(stack)(getSelectedSlot(stack))).map(i=>i.getIconIndex)

  def getNextModeIcon(stack: ItemStack): Option[Icon] = {
    val c = getContents(stack)
    val s = getSelectedSlot(stack)
    if (s + 1 >= c.size) Some(c(0).getIconIndex) else Some(c(s + 1).getIconIndex)
  }

  def cycleMode(stack: ItemStack, dmode: Int) {
    val s = getSelectedSlot(stack)
    if (dmode > 0) {
      setSelectedSlot(stack, (s + dmode) % getNumStacks(stack))
    } else {
      setSelectedSlot(stack, ((s + dmode) - getNumStacks(stack) * dmode) % getNumStacks(stack))
    }
  }

  def canStoreItem(stack: ItemStack): Boolean = stack.getItem.isInstanceOf[ItemFood]
}
