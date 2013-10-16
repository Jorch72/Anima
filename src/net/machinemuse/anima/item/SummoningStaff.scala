package net.machinemuse.anima.item

import net.machinemuse.numina.item.ModeChangingItem
import net.minecraft.item.ItemStack
import net.minecraft.util.Icon
import net.minecraft.client.renderer.texture.IconRegister
import net.machinemuse.anima.spirit.GreatSpiritListings

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:52 PM, 10/15/13
 */
object SummoningStaff extends AnimaItemBase("summoningstaff") with ModeChangingItem {
  def getPrevModeIcon(stack: ItemStack): Option[Icon] = {
    val index = currentSpiritIndex(stack)
    if (index > 0) Some(GreatSpiritListings.listings(index - 1).getIcon)
    else Some(GreatSpiritListings.listings.last.getIcon)
  }

  def getCurrentModeIcon(stack: ItemStack): Option[Icon] = GreatSpiritListings.listings.find(sp => sp.name == getActiveMode(stack)).map(sp => sp.getIcon)

  def getNextModeIcon(stack: ItemStack): Option[Icon] = {
    Some(GreatSpiritListings.listings((currentSpiritIndex(stack) + 1) % GreatSpiritListings.listings.size).getIcon)
  }

  def currentSpiritIndex(stack: ItemStack) = GreatSpiritListings.listings.indexWhere(sp => sp.name == getActiveMode(stack))

  def cycleMode(stack: ItemStack, dmode: Int): Unit =
    GreatSpiritListings.listings((currentSpiritIndex(stack) + dmode) % GreatSpiritListings.listings.size)


  override val maxDamage = 0
  override val noRepair = true
  override val maxstacksize = 1

  override def registerIcons(register: IconRegister) {

    for (spirit <- GreatSpiritListings.listings) {
      spirit.registerIcons(register)
    }
  }
}
