package net.machinemuse.anima.plants

import net.machinemuse.anima.block.AnimaTileEntity
import net.machinemuse.anima.block.plants.PlantPartRegistry
import net.minecraft.nbt.NBTTagCompound

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:35 PM, 7/3/13
 */
class PlantTileEntity extends AnimaTileEntity {
  var growthProgress: Byte = 0
  var plantPart: String = ""

  def fertilize() {
    PlantPartRegistry.get(plantPart) map {
      p =>
        p.growth.grow(this)
        updateContainingBlockInfo()
    }
  }

  override def canUpdate = false

  override def updateEntity() {
    growthProgress = (growthProgress + 1).toByte
    if (growthProgress > 0) {
      fertilize()
      growthProgress = 0
    }
  }

  override def writeToNBT(nbt: NBTTagCompound) {
    super.writeToNBT(nbt)
    nbt.setByte("g", growthProgress)
    nbt.setString("p", plantPart)
  }

  override def readFromNBT(nbt: NBTTagCompound) {
    super.readFromNBT(nbt)
    growthProgress = nbt.getByte("g")
    plantPart = nbt.getString("p")
  }
}
