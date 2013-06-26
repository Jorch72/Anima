package net.machinemuse.anima

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import net.minecraft.world.IBlockAccess
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderBlocks

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:35 PM, 6/25/13
 */
trait CustomBlockRenderer extends ISimpleBlockRenderingHandler {
  def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {}

  def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    val a = 0 // some value representing what type it is
    a match {
      case 0=> renderer.renderCrossedSquares(block, x,y,z) // X shape
      case 1=> renderer.renderBlockCrops(block,x,y,z) // # shape
    }
  }

  def shouldRender3DInInventory(): Boolean = false

  def getRenderId: Int = ???
}
