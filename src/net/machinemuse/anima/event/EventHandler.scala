package net.machinemuse.anima.event

import net.minecraftforge.event.ForgeSubscribe
import net.minecraftforge.event.entity.player.{EntityInteractEvent, PlayerInteractEvent}
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action
import net.minecraft.item.Item
import net.machinemuse.anima.item.Kettle

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
    val bid = world.getBlockId(e.x,e.y,e.z)
    if(bid == 8 && e.entityPlayer.getCurrentEquippedItem.getItem.equals(Item.bowlEmpty)) {
      val i = e.entityPlayer.inventory
      i.setInventorySlotContents(i.currentItem, Kettle.createItemStack)
    }
  }

  def onPlayerLeftClickBlock(e: PlayerInteractEvent) {}

  @ForgeSubscribe
  def onEntityInteract(e: EntityInteractEvent) {

  }
}
