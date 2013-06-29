package net.machinemuse.anima.block.plants

import net.machinemuse.anima.plants._
import net.machinemuse.anima.plants.NoGrowth
import net.machinemuse.anima.plants.GrowInPlace
import net.machinemuse.anima.plants.GrowUp
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:02 PM, 6/18/13
 */
class PlantPart(val name: String, val render: PlantRenderType, val growth: GrowthBehaviour = NoGrowth(), val break: BreakBehaviour = Break(), val drop: DropBehaviour = DropNothing()) {
  def itemStack:ItemStack = {
    val is = new ItemStack(PlantBlock.block)
    val nbt = new NBTTagCompound()
    is.setTagCompound(nbt)
    nbt.setString("t", name)
    is
  }

  PlantPartRegistry.put(name, this)
}

object WoadSprout extends PlantPart("anima.woadSprout", PlantRenderCross("woadsprout"), GrowInPlace(WoadLeavesTrimmed))

object WoadLeavesTrimmed extends PlantPart("anima.woadLeavesTrimmed", PlantRenderCross("woadleavestrimmed"), GrowInPlace(WoadLeavesFull))

object WoadLeavesFull extends PlantPart("anima.woadLeavesFull", PlantRenderCross("woadleavesfull"), GrowUp(WoadFlowers), break = Regress(WoadLeavesTrimmed))

object WoadFlowers extends PlantPart("anima.woadFlowers", PlantRenderHash("woadflowers"))

//object LotusSprout extends PlantPart("anima.lotusSprout", PlantRenderCross("lotussprout.png"))
//
//object LotusTuber extends PlantPart("anima.lotusTuber", PlantRenderHash("lotustuber.png"))

