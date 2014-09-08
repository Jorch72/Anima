package net.machinemuse.anima

import net.minecraft.item.ItemStack
import net.minecraft.creativetab.CreativeTabs
import cpw.mods.fml.common.registry.LanguageRegistry
import net.machinemuse.anima.block.AnimaBlock
import net.machinemuse.anima.item.DreamCatcher

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:28 PM, 6/18/13
 */

object AnimaTab extends CreativeTabs(CreativeTabs.getNextID, "Anima") {
  override def getIconItemStack: ItemStack = new ItemStack(DreamCatcher)

}
