package net.machinemuse.anima.event

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.machinemuse.anima.entity.EntityGreatCow
import net.machinemuse.anima.item.Kettle
import net.machinemuse.anima.spirit.GreatCow
import net.machinemuse.numina.general.MuseLogger
import net.machinemuse.numina.scala.OptionCast
import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.passive._
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.event.entity.item.ItemTossEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action
import net.minecraftforge.event.entity.player.{EntityInteractEvent, PlayerInteractEvent}

import scala.collection.JavaConverters._

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:54 PM, 6/28/13
 */
object EventHandler {
  @SubscribeEvent
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
    val block = world.getBlock(e.x, e.y, e.z)
    // TODO: Find out of block is water
//    if (block.getBlock && e.entityPlayer.getCurrentEquippedItem.getItem.equals(Item.bowlEmpty)) {
//      val i = e.entityPlayer.inventory
//      i.setInventorySlotContents(i.currentItem, new ItemStack(Kettle))
//    }
  }

  def onPlayerLeftClickBlock(e: PlayerInteractEvent) {}

  @SubscribeEvent
  def onEntityInteract(e: EntityInteractEvent) {
    val target = e.target
    val player = e.entityPlayer

    for (
      animal <- OptionCast[EntityAnimal](target);
      stack <- Option(player.getCurrentEquippedItem)
      if animal.isBreedingItem(stack)
      if !animal.isInLove
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

  @SubscribeEvent
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
