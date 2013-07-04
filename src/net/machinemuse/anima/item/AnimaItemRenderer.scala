package net.machinemuse.anima.item

import net.machinemuse.utils.MuseItemRenderer
import net.minecraft.item.ItemStack
import net.minecraft.client.renderer.{RenderEngine, RenderBlocks}
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.storage.MapData
import net.minecraft.entity.item.EntityItem

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:42 PM, 7/3/13
 */
object AnimaItemRenderer extends MuseItemRenderer {
  // Item alone as an entity e.g. dropped
  def renderEntity(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityItem) {}

  // Inventory screen / toolbar
  def renderInventory(item: ItemStack, renderBlocks: RenderBlocks) {}

  // Maps only e.g. thaumometer
  def renderFirstPersonMap(item: ItemStack, entity: EntityPlayer, engine: RenderEngine, data: MapData) {}

  // First person fist
  def renderFirstPerson(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityLiving) {}

  // Entity equipped in the world
  def renderEquipped(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityLiving) {}
}
