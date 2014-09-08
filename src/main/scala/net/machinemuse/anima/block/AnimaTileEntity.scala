package net.machinemuse.anima.block

import net.minecraft.tileentity.TileEntity
import net.minecraft.network.INetworkManager
import net.minecraft.network.packet.{Packet, Packet132TileEntityData}
import net.minecraft.nbt.NBTTagCompound

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:40 AM, 6/29/13
 */
class AnimaTileEntity extends TileEntity {
  override def onDataPacket(net: INetworkManager, pkt: Packet132TileEntityData) {
    readFromNBT(pkt.data)
    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
  }

  override def getDescriptionPacket: Packet = {
    val tag: NBTTagCompound = new NBTTagCompound
    writeToNBT(tag)
    new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag)
  }
}
