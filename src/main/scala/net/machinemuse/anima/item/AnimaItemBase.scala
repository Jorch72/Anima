package net.machinemuse.anima.item

import cpw.mods.fml.common.registry.GameRegistry
import net.machinemuse.anima.{Anima, AnimaTab}
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.item.NuminaItemBase
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.Item

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:46 PM, 10/15/13
 */
abstract class AnimaItemBase(val name: String) extends Item with NuminaItemBase {
  GameRegistry.registerItem(this, name, Anima.modid)
  MuseLogger.logDebug("Anima loaded item " + name)
  setCreativeTab(AnimaTab)

  def iconRegistration(register: IIconRegister) {
    this.itemIcon = register.registerIcon("anima:" + name)
  }

}