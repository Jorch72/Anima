package net.machinemuse.anima.item

import net.minecraft.item.Item
import net.machinemuse.anima.AnimaTab
import net.machinemuse.numina.item.NuminaItemBase

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:46 PM, 10/15/13
 */
abstract class AnimaItemBase(val unlocalizedName: String) extends Item(ItemIDManager.getID(unlocalizedName)) with AnimaItem {

}

trait AnimaItem extends NuminaItemBase {
  override val creativeTab = AnimaTab
}