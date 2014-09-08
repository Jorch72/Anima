package net.machinemuse.anima.plants

import cpw.mods.fml.client.registry.{RenderingRegistry, ISimpleBlockRenderingHandler}
import net.minecraft.world.IBlockAccess
import net.minecraft.block.Block
import net.minecraft.client.renderer.{Tessellator, RenderBlocks}
import net.minecraft.client.renderer.texture.{TextureManager, IconRegister}
import net.minecraft.util.Icon
import net.minecraft.item.ItemStack
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.storage.MapData
import net.minecraft.entity.item.EntityItem
import org.lwjgl.opengl.GL11
import net.machinemuse.numina.item.MuseItemRenderer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:47 PM, 6/18/13
 */
object PlantRenderer extends ISimpleBlockRenderingHandler with MuseItemRenderer {
  override val getRenderId: Int = RenderingRegistry.getNextAvailableRenderId

  def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {}

  def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    PlantPartRegistry.getPlantPart(world, x, y, z) match {
      case Some(part) => part.render(x, y, z, renderer, block.getMixedBrightnessForBlock(world, x, y, z))
      case None =>
    }
    true
  }

  def shouldRender3DInInventory(): Boolean = true

  // Item alone as an entity e.g. dropped
  def renderEntity(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityItem) {
    PlantPartRegistry.getPlantPart(item).map {
      part => part.render(0, 0, 0, renderBlocks)
    }
  }

  // Inventory screen / toolbar
  def renderInventory(item: ItemStack, renderBlocks: RenderBlocks) {
    PlantPartRegistry.getPlantPart(item).map {
      part =>
        GL11.glDisable(GL11.GL_CULL_FACE)
        Tessellator.instance.startDrawingQuads()
        part.render(0, 0, 0, renderBlocks)
        Tessellator.instance.draw()
    }
  }

  // Maps only e.g. thaumometer
  def renderFirstPersonMap(item: ItemStack, entity: EntityPlayer, engine: TextureManager, data: MapData) {}

  // First person fist
  def renderFirstPerson(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityLivingBase) {
    PlantPartRegistry.getPlantPart(item).map {
      part => part.render(0, 0, 0, renderBlocks)
    }
  }

  // Entity equipped in the world
  def renderEquipped(item: ItemStack, renderBlocks: RenderBlocks, entity: EntityLivingBase) {
    PlantPartRegistry.getPlantPart(item).map {
      part => part.render(0, 0, 0, renderBlocks)
    }
  }
}

abstract class PlantRenderType {
  def registerIcon(register: IconRegister)

  var icon: Icon = null

  def apply(x: Int, y: Int, z: Int, r: RenderBlocks, brightness: Int = 255)
}

case class PlantRenderCross(iconpath: String) extends PlantRenderType {
  def registerIcon(register: IconRegister) {
    icon = register.registerIcon("Anima:" + iconpath)
  }

  val height = 1.0f

  def apply(x: Int, y: Int, z: Int, r: RenderBlocks, brightness: Int = 255) {
    val tessellator = Tessellator.instance
    tessellator.setBrightness(brightness)
    tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F)
    val u1: Double = icon.getMinU
    val v1: Double = icon.getMinV
    val u2: Double = icon.getMaxU
    val v2: Double = icon.getMaxV
    val d7: Double = 0.45D * height
    val d8: Double = x + 0.5D - d7
    val d9: Double = x + 0.5D + d7
    val d10: Double = z + 0.5D - d7
    val d11: Double = z + 0.5D + d7
    tessellator.addVertexWithUV(d8, y + height, d10, u1, v1)
    tessellator.addVertexWithUV(d8, y + 0.0D, d10, u1, v2)
    tessellator.addVertexWithUV(d9, y + 0.0D, d11, u2, v2)
    tessellator.addVertexWithUV(d9, y + height, d11, u2, v1)
    tessellator.addVertexWithUV(d8, y + height, d11, u1, v1)
    tessellator.addVertexWithUV(d8, y + 0.0D, d11, u1, v2)
    tessellator.addVertexWithUV(d9, y + 0.0D, d10, u2, v2)
    tessellator.addVertexWithUV(d9, y + height, d10, u2, v1)
  }

}

case class PlantRenderHash(iconpath: String) extends PlantRenderType {
  def registerIcon(register: IconRegister) {
    icon = register.registerIcon("Anima:" + iconpath)
  }

  def apply(x: Int, y: Int, z: Int, r: RenderBlocks, brightness: Int) {
    val tessellator = Tessellator.instance
    tessellator.setBrightness(brightness)
    tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F)
    val d3: Double = icon.getMinU
    val d4: Double = icon.getMinV
    val d5: Double = icon.getMaxU
    val d6: Double = icon.getMaxV
    var d7: Double = x + 0.5D - 0.25D
    var d8: Double = x + 0.5D + 0.25D
    var d9: Double = z + 0.5D - 0.5D
    var d10: Double = z + 0.5D + 0.5D
    tessellator.addVertexWithUV(d7, y + 1.0D, d9, d3, d4)
    tessellator.addVertexWithUV(d7, y + 0.0D, d9, d3, d6)
    tessellator.addVertexWithUV(d7, y + 0.0D, d10, d5, d6)
    tessellator.addVertexWithUV(d7, y + 1.0D, d10, d5, d4)
    tessellator.addVertexWithUV(d8, y + 1.0D, d10, d3, d4)
    tessellator.addVertexWithUV(d8, y + 0.0D, d10, d3, d6)
    tessellator.addVertexWithUV(d8, y + 0.0D, d9, d5, d6)
    tessellator.addVertexWithUV(d8, y + 1.0D, d9, d5, d4)
    d7 = x + 0.5D - 0.5D
    d8 = x + 0.5D + 0.5D
    d9 = z + 0.5D - 0.25D
    d10 = z + 0.5D + 0.25D
    tessellator.addVertexWithUV(d7, y + 1.0D, d9, d3, d4)
    tessellator.addVertexWithUV(d7, y + 0.0D, d9, d3, d6)
    tessellator.addVertexWithUV(d8, y + 0.0D, d9, d5, d6)
    tessellator.addVertexWithUV(d8, y + 1.0D, d9, d5, d4)
    tessellator.addVertexWithUV(d8, y + 1.0D, d10, d3, d4)
    tessellator.addVertexWithUV(d8, y + 0.0D, d10, d3, d6)
    tessellator.addVertexWithUV(d7, y + 0.0D, d10, d5, d6)
    tessellator.addVertexWithUV(d7, y + 1.0D, d10, d5, d4)
  }

}