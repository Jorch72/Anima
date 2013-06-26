package net.machinemuse.anima.block.plants

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import net.minecraft.world.IBlockAccess
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderBlocks

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:47 PM, 6/18/13
 */
class PlantRenderer extends ISimpleBlockRenderingHandler {
  def getRenderId: Int = PlantBlock.id

  def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {}

  def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = true

  def shouldRender3DInInventory(): Boolean = true

}
