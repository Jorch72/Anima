package net.machinemuse.anima.item

import net.minecraft.item.{ItemFood, ItemStack}
import net.minecraft.util.Icon
import net.machinemuse.numina.item.{InventoriedItem, ModeChangingItem}
import net.minecraft.entity.player.EntityPlayer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:16 PM, 8/7/13
 */
object Basket
  extends AnimaItemBase("basket")
  with ModeChangingItem
  with InventoriedItem {

  def getModeIcon(mode: String, stack: ItemStack, player: EntityPlayer): Option[Icon] =
    if (getContents(stack).size > getSelectedSlot(stack)) {
      Option(getContents(stack)(getSelectedSlot(stack))).map(i => i.getIconIndex)
    } else {
      None
    }


  def canStoreItem(stack: ItemStack): Boolean = stack.getItem.isInstanceOf[ItemFood]

  val maxdamage: Int = 0
  val noRepair: Boolean = true
  val maxstacksize: Int = 1

  def getValidModes(stack: ItemStack, player: EntityPlayer): Seq[String] = {
    for(k <- 0 until getContents(stack).size) yield k.toString
  }
}
