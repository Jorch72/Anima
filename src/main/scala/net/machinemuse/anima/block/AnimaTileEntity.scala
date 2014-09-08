package net.machinemuse.anima.block

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.tileentity.TileEntity

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:40 AM, 6/29/13
 */
class AnimaTileEntity extends TileEntity {
  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity) {
    readFromNBT(pkt.func_148857_g()) // getData
    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
  }

  override def getDescriptionPacket: Packet = {
    val tag: NBTTagCompound = new NBTTagCompound
    writeToNBT(tag)
    new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag)
  }
}
