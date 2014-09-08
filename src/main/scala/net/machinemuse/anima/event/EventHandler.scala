package net.machinemuse.anima.event

import net.minecraftforge.event.ForgeSubscribe
import net.minecraftforge.event.entity.player.{EntityInteractEvent, PlayerInteractEvent}
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action
import net.minecraft.item.{ItemStack, Item}
import net.machinemuse.anima.item.Kettle
import net.minecraft.entity.passive._
import net.machinemuse.numina.scala.OptionCast
import net.machinemuse.anima.spirit.GreatCow
import net.minecraftforge.event.entity.item.ItemTossEvent
import scala.collection.JavaConverters._
import net.machinemuse.anima.entity.EntityGreatCow
import net.machinemuse.numina.general.MuseLogger
import net.minecraft.entity.EntityLivingBase

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
        case a: EntityCow => GreatCow.addFavour(player, 10)
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

  @ForgeSubscribe
  def onTossItem(e: ItemTossEvent) {
    val player = e.player
    val world = e.player.worldObj
    MuseLogger.logDebug("Tossed " + e.entityItem.getEntityItem)
    val list = world.getEntitiesWithinAABB(classOf[EntityLivingBase], player.boundingBox.expand(60.0, 60.0, 60.0))
    for (entity <- list.asScala) {
      entity match {
        case cow: EntityGreatCow => if (!e.isCanceled) e.setCanceled(GreatCow.receiveOffering(player, e.entityItem, cow))
        case _ =>
      }
    }
  }
}
