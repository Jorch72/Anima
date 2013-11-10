package net.machinemuse.anima


import cpw.mods.fml.common.{FMLCommonHandler, Mod}
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.Mod.{PostInit, PreInit, Init}
import net.machinemuse.anima.block.{BlockCowTotem, AnimaBlock}
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLPreInitializationEvent, FMLInitializationEvent}
import cpw.mods.fml.relauncher.Side
import net.machinemuse.anima.plants.parts.WoadSprout
import net.machinemuse.anima.item.{Kettle, Basket, DreamCatcher, SummoningStaff}
import cpw.mods.fml.common.registry.{GameRegistry, EntityRegistry}
import net.machinemuse.anima.entity.{EntityGreatOcelot, EntityGreatCow}
import net.minecraftforge.common.MinecraftForge
import net.machinemuse.anima.event.EventHandler
import net.machinemuse.anima.plants.PlantTileEntity

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "anima", modLanguage = "scala", dependencies = "required-after:numina")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, tinyPacketHandler = classOf[AnimaPacketHandler])
object Anima {
  val modid = "anima"
  //@SidedProxy(clientSide = "net.machinemuse.anima.ClientProxy", serverSide = "net.machinemuse.anima.ServerProxy")
  var proxy: CommonProxy = if (FMLCommonHandler.instance().getSide == Side.CLIENT) new ClientProxy else new ServerProxy

  @PreInit def preinit(e: FMLPreInitializationEvent) {
    WoadSprout
    AnimaBlock.init(2481)
    DreamCatcher
    SummoningStaff
    Kettle
    Basket


    BlockCowTotem
    GameRegistry.registerBlock(BlockCowTotem, "cowtotem")


    MinecraftForge.EVENT_BUS.register(EventHandler)
    EntityRegistry.registerModEntity(classOf[EntityGreatCow], "entityGreatCowManifestation", 2588, this, 64, 20, true)
    EntityRegistry.registerModEntity(classOf[EntityGreatOcelot], "entityGreatOcelotManifestation", 2589, this, 64, 20, true)
    proxy.PreInit()
  }

  @Init def init(e: FMLInitializationEvent) {
    proxy.Init()
  }

  @PostInit def postinit(e: FMLPostInitializationEvent) {
    proxy.PostInit()
  }
}
