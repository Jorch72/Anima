package net.machinemuse.anima


import cpw.mods.fml.client.registry.{ClientRegistry, RenderingRegistry}
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.registry.{EntityRegistry, GameRegistry}
import cpw.mods.fml.common.{FMLCommonHandler, Mod}
import cpw.mods.fml.relauncher.Side
import net.machinemuse.anima.block._
import net.machinemuse.anima.entity.render.{BillboardRenderer, RenderGreatCow, RenderGreatOcelot}
import net.machinemuse.anima.entity.{AnimaEntityHarvestSprite, EntityGreatCow, EntityGreatOcelot}
import net.machinemuse.anima.event.EventHandler
import net.machinemuse.anima.item._
import net.machinemuse.anima.plants.parts.WoadSprout
import net.machinemuse.anima.plants.{PlantBlock, PlantRenderer}
import net.minecraft.entity.Entity
import net.minecraftforge.client.MinecraftForgeClient
import net.minecraftforge.common.MinecraftForge

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "anima", modLanguage = "scala", dependencies = "required-after:numina")
object Anima {
  val modid = "anima"
  var proxy: CommonProxy = if (FMLCommonHandler.instance().getSide == Side.CLIENT) new ClientProxy else new ServerProxy

  @Mod.EventHandler def preinit(e: FMLPreInitializationEvent) {
    WoadSprout
    AnimaBlock.init(2481)
    DreamCatcher
    SummoningStaff
    Kettle
    Basket
    Incense

    BlockCowTotem
    GameRegistry.registerBlock(BlockCowTotem, "cowtotem")

    GameRegistry.registerBlock(BlockIncenseBurner, "incenseburner")

    MinecraftForge.EVENT_BUS.register(EventHandler)
    registerEntity(classOf[EntityGreatCow], "entityGreatCowManifestation", 2588)
    registerEntity(classOf[EntityGreatOcelot], "entityGreatOcelotManifestation", 2589)
    registerEntity(classOf[AnimaEntityHarvestSprite], "entityHarvestSprite", 2590)
    proxy.PreInit()
  }

  @Mod.EventHandler def init(e: FMLInitializationEvent) {
    proxy.Init()
  }

  @Mod.EventHandler def postinit(e: FMLPostInitializationEvent) {
    proxy.PostInit()
  }

  def registerEntity(entityClass: Class[_ <: Entity], name: String, id: Int, trackingRange: Int = 64, ticksPerUpdate: Int = 1, sendsVelocityUpdates: Boolean = true) {
    EntityRegistry.registerModEntity(entityClass, name, id, this, trackingRange, ticksPerUpdate, sendsVelocityUpdates)
  }
}


class CommonProxy {
  def PreInit() {}

  def Init() {}

  def PostInit() {}
}

class ClientProxy extends CommonProxy {
  override def Init() {
    RenderingRegistry.registerBlockHandler(PlantRenderer)
    MinecraftForgeClient.registerItemRenderer(PlantBlock.item, PlantRenderer)


    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityIncenseBurner], IncenseBurnerRenderer)
    RenderingRegistry.registerBlockHandler(IncenseBurnerRenderer)

    RenderingRegistry.registerEntityRenderingHandler(classOf[EntityGreatCow], RenderGreatCow)
    RenderingRegistry.registerEntityRenderingHandler(classOf[EntityGreatOcelot], RenderGreatOcelot)
    RenderingRegistry.registerEntityRenderingHandler(classOf[AnimaEntityHarvestSprite], new BillboardRenderer(Anima.modid + ":textures/entities/forestsprite1.png", 0.5))
    //    MinecraftForgeClient.registerItemRenderer(AnimaItem.item.itemID, AnimaItemRenderer)
  }
}

class ServerProxy extends CommonProxy {

}