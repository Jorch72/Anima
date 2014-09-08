package net.machinemuse.anima.item

import net.machinemuse.numina.item.ModeChangingItem
import net.minecraft.item.ItemStack
import net.minecraft.util.{MathHelper, Icon}
import net.minecraft.client.renderer.texture.IconRegister
import net.machinemuse.anima.spirit.GreatSpiritListings
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.machinemuse.anima.entity.AnimaEntityHarvestSprite

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:52 PM, 10/15/13
 */
object SummoningStaff extends AnimaItemBase("summoningstaff") with ModeChangingItem {
  setMaxDamage(0)// 5 minutes
  setMaxStackSize(1)
  setNoRepair()

  def getModeIcon(mode: String, stack: ItemStack, player: EntityPlayer): Option[Icon] = GreatSpiritListings.listings.find(sp => sp.name == mode).map(sp => sp.getIcon)

  def getValidModes(stack: ItemStack, player: EntityPlayer) = {
    for (spirit <- GreatSpiritListings.listings) yield spirit.name
  }


  override def registerIcons(register: IconRegister) {
    super.registerIcons(register)
    for (spirit <- GreatSpiritListings.listings) {
      spirit.registerIcons(register)
    }
  }

  override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = {
    val newspirit = new AnimaEntityHarvestSprite(world)
    newspirit.setLocationAndAngles(player.posX, player.posY, player.posZ, MathHelper.wrapAngleTo180_float(world.rand.nextFloat * 360.0F), 0.0F)
    newspirit.tetherX = player.posX.toInt
    newspirit.tetherY = player.posY.toInt
    newspirit.tetherZ = player.posZ.toInt
    world.spawnEntityInWorld(newspirit)
    //    GreatSpiritListings.listings.find(sp => sp.name == getActiveMode(stack, player)).map(gs => gs.summon(world, player))
    stack
  }
}
