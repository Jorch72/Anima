package net.machinemuse.anima


import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.Mod.Init
import net.machinemuse.anima.block.EspritBlock
import cpw.mods.fml.common.event.FMLInitializationEvent

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:06 AM, 6/18/13
 */
@Mod(modid = "Anima", modLanguage = "scala")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, tinyPacketHandler = classOf[AnimaPacketHandler])
object Anima {
  @Init def init(e: FMLInitializationEvent) {
    //EspritBlock.init(2481)
  }
}
