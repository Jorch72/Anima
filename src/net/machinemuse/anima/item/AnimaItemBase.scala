package net.machinemuse.anima.item

import net.minecraft.item.Item
import net.machinemuse.anima.AnimaTab
import net.machinemuse.numina.item.NuminaItemBase
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.util.Icon

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:46 PM, 10/15/13
 */
abstract class AnimaItemBase(val name: String) extends Item(ItemIDManager.getID(name)) with AnimaItem {
  var icon:Icon=null
  def iconRegistration(register:IconRegister) {
    icon = register.registerIcon("anima:" + name)
  }
}

trait AnimaItem extends NuminaItemBase {
  override val creativeTab = AnimaTab
}