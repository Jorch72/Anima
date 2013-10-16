package net.machinemuse.anima.event

import net.minecraftforge.event.ForgeSubscribe
import net.minecraftforge.event.entity.player.{EntityInteractEvent, PlayerInteractEvent}
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action
import net.minecraft.item.{ItemStack, Item}
import net.machinemuse.anima.item.Kettle
import net.minecraft.entity.passive._
import net.machinemuse.numina.scala.OptionCast
import net.machinemuse.anima.spirit.GreatCow

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:54 PM, 6/28/13
 */
object EventHandler {
  @ForgeSubscribe
  def onPlayerRightClick(e: PlayerInteractEvent) {
    e.action match {
      case Action.RIGHT_CLICK_AIR => onPlayerRightClickAir(e)
      case Action.RIGHT_CLICK_BLOCK => onPlayerRightClickBlock(e)
      case Action.LEFT_CLICK_BLOCK => onPlayerLeftClickBlock(e)
    }
  }

  def onPlayerRightClickAir(e: PlayerInteractEvent) {}

  def onPlayerRightClickBlock(e: PlayerInteractEvent) {
    val world = e.entityPlayer.worldObj
    val bid = world.getBlockId(e.x, e.y, e.z)
    if (bid == 8 && e.entityPlayer.getCurrentEquippedItem.getItem.equals(Item.bowlEmpty)) {
      val i = e.entityPlayer.inventory
      i.setInventorySlotContents(i.currentItem, new ItemStack(Kettle))
    }
  }

  def onPlayerLeftClickBlock(e: PlayerInteractEvent) {}

  @ForgeSubscribe
  def onEntityInteract(e: EntityInteractEvent) {
    val target = e.target
    val player = e.entityPlayer

    for (
      animal <- OptionCast[EntityAnimal](target);
      stack <- Option(player.getCurrentEquippedItem)
      if animal.isBreedingItem(stack)
      if animal.inLove <= 0
      if animal.getGrowingAge == 0
    ) {
      // Animal being fed breeding food
      animal match {
        case a: EntityCow => GreatCow.addFavour(10, player)
        case a: EntitySheep =>
        case a: EntityWolf =>
        case a: EntityChicken =>
        case a: EntityPig =>
        case a: EntityHorse =>
        case a: EntityOcelot =>
        case _ =>
      }

    }
  }
}
