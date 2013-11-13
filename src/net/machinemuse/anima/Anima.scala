package net.machinemuse.anima


import cpw.mods.fml.common.{Mod, FMLCommonHandler}
import cpw.mods.fml.common.network.NetworkMod
import net.machinemuse.anima.block.{BlockIncenseBurner, BlockCowTotem, AnimaBlock}
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLPreInitializationEvent, FMLInitializationEvent}
import cpw.mods.fml.relauncher.Side
import net.machinemuse.anima.plants.parts.WoadSprout
import net.machinemuse.anima.item.{Kettle, Basket, DreamCatcher, SummoningStaff}
import cpw.mods.fml.common.registry.{GameRegistry, EntityRegistry}
import net.machinemuse.anima.entity.{AnimaEntityHarvestSprite, EntityGreatOcelot, EntityGreatCow}
import net.minecraftforge.common.MinecraftForge
import net.machinemuse.anima.event.EventHandler
import net.minecraft.entity.Entity

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "anima", modLanguage = "scala", dependencies = "required-after:numina")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, tinyPacketHandler = classOf[AnimaPacketHandler])
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

  def registerEntity(entityClass: Class[_ <: Entity], name: String, id: Int, trackingRange: Int = 64, ticksPerUpdate: Int = 20, sendsVelocityUpdates: Boolean = true) {
    EntityRegistry.registerModEntity(entityClass, name, id, this, trackingRange, ticksPerUpdate, sendsVelocityUpdates)
  }
}
