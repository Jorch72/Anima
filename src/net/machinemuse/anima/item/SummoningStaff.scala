package net.machinemuse.anima.item

import net.machinemuse.numina.item.ModeChangingItem
import net.minecraft.item.ItemStack
import net.minecraft.util.Icon
import net.minecraft.client.renderer.texture.IconRegister
import net.machinemuse.anima.spirit.GreatSpiritListings
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.machinemuse.anima.entity.AnimaEntityGrowthSpirit

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:52 PM, 10/15/13
 */
object SummoningStaff extends AnimaItemBase("summoningstaff") with ModeChangingItem {
  override val maxdamage = 0
  override val noRepair = true
  override val maxstacksize = 1


  def getModeIcon(mode: String, stack: ItemStack, player: EntityPlayer): Option[Icon] = GreatSpiritListings.listings.find(sp => sp.name == mode).map(sp => sp.getIcon)

  def getValidModes(stack: ItemStack, player:EntityPlayer) = {
    for (spirit <- GreatSpiritListings.listings) yield spirit.name
  }


  override def registerIcons(register: IconRegister) {
    super.registerIcons(register)
    for (spirit <- GreatSpiritListings.listings) {
      spirit.registerIcons(register)
    }
  }

  override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = {

    world.spawnEntityInWorld(new AnimaEntityGrowthSpirit(world))
//    GreatSpiritListings.listings.find(sp => sp.name == getActiveMode(stack, player)).map(gs => gs.summon(world, player))
    stack
  }
}
