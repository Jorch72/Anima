package net.machinemuse.anima

import cpw.mods.fml.client.registry.RenderingRegistry
import net.minecraftforge.client.MinecraftForgeClient
import net.machinemuse.anima.plants.{PlantBlock, PlantRenderer}
import net.machinemuse.anima.entity.render.{RenderGreatOcelot, RenderGreatCow}
import net.machinemuse.anima.entity.{AnimaEntityHarvestSprite, EntityGreatOcelot, EntityGreatCow}
import net.minecraft.client.renderer.entity.RenderSnowball
import net.machinemuse.anima.item.DreamCatcher

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:50 PM, 6/28/13
 */
class CommonProxy {
  def PreInit() {}

  def Init() {}

  def PostInit() {}
}

class ClientProxy extends CommonProxy {
  override def Init() {
    RenderingRegistry.registerBlockHandler(PlantRenderer)
    MinecraftForgeClient.registerItemRenderer(PlantBlock.item.itemID, PlantRenderer)



    RenderingRegistry.registerEntityRenderingHandler(classOf[EntityGreatCow], RenderGreatCow)
    RenderingRegistry.registerEntityRenderingHandler(classOf[EntityGreatOcelot], RenderGreatOcelot)
    RenderingRegistry.registerEntityRenderingHandler(classOf[AnimaEntityHarvestSprite], new RenderSnowball(DreamCatcher))
    //    MinecraftForgeClient.registerItemRenderer(AnimaItem.item.itemID, AnimaItemRenderer)
  }
}

class ServerProxy extends CommonProxy {

}