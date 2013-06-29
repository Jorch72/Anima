package net.machinemuse.anima.block.plants

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import net.minecraft.world.IBlockAccess
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderEngine, RenderBlocks}
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.util.Icon
import net.minecraft.item.ItemStack
import net.machinemuse.anima.plants.MuseItemRenderer
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.storage.MapData
import net.minecraft.entity.item.EntityItem

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:47 PM, 6/18/13
 */
object PlantRenderer extends ISimpleBlockRenderingHandler with MuseItemRenderer {
  def getRenderId: Int = PlantBlock.block.blockID

  def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {}

  def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    PlantPartRegistry.getPlantPart(world, x, y, z) map {
      part =>
        part.render.render(x, y, z, renderer)
    }
    true
  }

  def shouldRender3DInInventory(): Boolean = true

  // Item alone as an entity e.g. dropped
  def renderEntity(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityItem) {

  }

  // Inventory screen / toolbar
  def renderInventory(item: ItemStack, renderBlocks: RenderBlocks) {}

  // Maps only e.g. thaumometer
  def renderFirstPersonMap(item: ItemStack, entity: EntityPlayer, engine: RenderEngine, data: MapData) {}

  // First person fist
  def renderFirstPerson(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityLiving) {}

  // Entity equipped in the world
  def renderEquipped(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityLiving) {}
}

abstract class PlantRenderType {
  def registerIcon(register: IconRegister)

  var icon: Icon = null

  def render(x: Int, y: Int, z: Int, r: RenderBlocks)
}

case class PlantRenderCross(iconpath: String) extends PlantRenderType {
  def registerIcon(register: IconRegister) {
    icon = register.registerIcon("Anima:" + iconpath)
  }

  def render(x: Int, y: Int, z: Int, r: RenderBlocks) {}

}

case class PlantRenderHash(iconpath: String) extends PlantRenderType {
  def registerIcon(register: IconRegister) {
    icon = register.registerIcon("Anima:" + iconpath)
  }

  def render(x: Int, y: Int, z: Int, r: RenderBlocks) {}

}