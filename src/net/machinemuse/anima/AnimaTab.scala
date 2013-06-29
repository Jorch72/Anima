package net.machinemuse.anima

import net.minecraft.item.ItemStack
import net.minecraft.creativetab.CreativeTabs
import cpw.mods.fml.common.registry.LanguageRegistry
import net.machinemuse.anima.block.AnimaBlock

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:28 PM, 6/18/13
 */

object AnimaTab extends CreativeTabs(CreativeTabs.getNextID, "itemGroup.Anima") {
  LanguageRegistry.instance.addStringLocalization("itemGroup.Anima", "en_US", "Anima")

  override def getIconItemStack: ItemStack = new ItemStack(AnimaBlock.plantBlock)

}
