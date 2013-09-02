package net.machinemuse.anima.plants

import net.machinemuse.utils.{OptionCast, MuseRegistry}
import net.minecraft.world.IBlockAccess
import net.minecraft.item.ItemStack

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:09 PM, 6/18/13
 */
object PlantPartRegistry extends MuseRegistry[PlantPart] {
  def getPlantTileEntity(world: IBlockAccess, x: Int, y: Int, z: Int) = OptionCast[PlantTileEntity](world.getBlockTileEntity(x, y, z))

  def getPlantPart(world: IBlockAccess, x: Int, y: Int, z: Int): Option[PlantPart] = getPlantTileEntity(world, x, y, z).flatMap(e => get(e.plantPart))

  def getPlantPart(is: ItemStack): Option[PlantPart] = {
    if (is.hasTagCompound) {
      val str = is.getTagCompound.getString("t")
      if (str != "") return PlantPartRegistry.get(str)
    }
    None
  }
}
