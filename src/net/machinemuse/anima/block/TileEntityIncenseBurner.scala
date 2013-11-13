package net.machinemuse.anima.block

import net.minecraft.item.ItemStack
import net.machinemuse.anima.item.Incense
import net.minecraft.nbt.NBTTagCompound
import net.machinemuse.numina.tileentity.MuseTileEntity
import net.machinemuse.numina.random.MuseRandom

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:59 AM, 11/13/13
 */
class TileEntityIncenseBurner extends MuseTileEntity {
  var incense: Option[ItemStack] = None
  var isBurning = false

  def hasIncense = {
    incense match {
      case Some(i) => i.getItem eq Incense
      case None => false
    }
  }

  override def updateEntity() {
    if (isBurning) {
      incense match {
        case Some(stack) =>
          stack.attemptDamageItem(1, MuseRandom)
        case None => isBurning = false
      }
    }
  }

  override def writeToNBT(nbt: NBTTagCompound) {
    super.writeToNBT(nbt)
    incense map {
      stack =>
        writeItemStack(nbt, "incense", stack)
    }
  }

  override def readFromNBT(nbt: NBTTagCompound) {
    super.readFromNBT(nbt)
    getItemStack(nbt, "incense") map {
      is => incense = Some(is)
    }
  }

  override def canUpdate: Boolean = true
}