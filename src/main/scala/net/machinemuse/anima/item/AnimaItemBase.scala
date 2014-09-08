package net.machinemuse.anima.item

import net.minecraft.item.Item
import net.machinemuse.anima.{Anima, AnimaTab}
import net.machinemuse.numina.item.NuminaItemBase
import net.minecraft.client.renderer.texture.IconRegister
import cpw.mods.fml.common.registry.GameRegistry
import net.machinemuse.numina.general.MuseLogger

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:46 PM, 10/15/13
 */
abstract class AnimaItemBase(val name: String) extends Item(ItemIDManager.getID(name)) with NuminaItemBase {
  GameRegistry.registerItem(this, name, Anima.modid)
  MuseLogger.logDebug("Anima loaded item " + name)
  setCreativeTab(AnimaTab)

  def iconRegistration(register: IconRegister) {
    this.itemIcon = register.registerIcon("anima:" + name)
  }

}