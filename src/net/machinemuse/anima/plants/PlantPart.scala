package net.machinemuse.anima.block.plants

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.machinemuse.anima.plants.behaviour._
import net.machinemuse.anima.plants.behaviour.NoGrowth

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:02 PM, 6/18/13
 */
class PlantPart(val name: String, val render: PlantRenderType, val growth: GrowthBehaviour = NoGrowth(), val break: BreakBehaviour = Break(), val drop: DropBehaviour = DropNothing()) {
  def itemStack: ItemStack = itemStack(1)

  def itemStack(quantity:Int): ItemStack = {
    val is = new ItemStack(PlantBlock.block)
    val nbt = new NBTTagCompound()
    is.setTagCompound(nbt)
    nbt.setString("t", name)
    is
  }

  PlantPartRegistry.put(name, this)
}


//object LotusSprout extends PlantPart("anima.lotusSprout", PlantRenderCross("lotussprout.png"))
//
//object LotusTuber extends PlantPart("anima.lotusTuber", PlantRenderHash("lotustuber.png"))

