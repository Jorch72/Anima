package net.machinemuse.anima


import cpw.mods.fml.common.{FMLCommonHandler, Mod}
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.Mod.{PostInit, PreInit, Init}
import net.machinemuse.anima.block.AnimaBlock
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLPreInitializationEvent, FMLInitializationEvent}
import cpw.mods.fml.relauncher.Side
import net.machinemuse.anima.plants.parts.WoadSprout
import net.machinemuse.anima.item.AnimaItem

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "Anima", modLanguage = "scala")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, tinyPacketHandler = classOf[AnimaPacketHandler])
object Anima {
  //@SidedProxy(clientSide = "net.machinemuse.anima.ClientProxy", serverSide = "net.machinemuse.anima.ServerProxy")
  var proxy: CommonProxy = if (FMLCommonHandler.instance().getSide == Side.CLIENT) new ClientProxy else new ServerProxy

  @PreInit def preinit(e: FMLPreInitializationEvent) {
    WoadSprout
    AnimaBlock.init(2481)
    proxy.PreInit()
  }

  @Init def init(e: FMLInitializationEvent) {
    proxy.Init()
  }

  @PostInit def postinit(e: FMLPostInitializationEvent) {
    proxy.PostInit()
  }
}
