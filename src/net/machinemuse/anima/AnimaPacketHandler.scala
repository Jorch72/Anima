package net.machinemuse.anima

import cpw.mods.fml.common.network.{ITinyPacketHandler, Player, IPacketHandler}
import net.minecraft.network.INetworkManager
import net.minecraft.network.packet.{Packet131MapData, NetHandler, Packet250CustomPayload}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:27 AM, 6/18/13
*/
class AnimaPacketHandler extends ITinyPacketHandler {
  def handle(handler: NetHandler, mapData: Packet131MapData) {}
}
