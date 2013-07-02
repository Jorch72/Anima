package net.machinemuse.anima.item

import net.minecraft.item.{ItemStack, Item}
import net.machinemuse.utils.MuseRegistry
import net.minecraft.nbt.NBTTagCompound
import net.machinemuse.anima.AnimaTab

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:29 AM, 6/18/13
 */
object AnimaItem extends MuseRegistry[AnimaSubItem] {
  var item:AnimaItem = null

  def lookUpItemStack(is:ItemStack):Option[AnimaSubItem] = {
    Option(is.getTagCompound).flatMap {
      tag=>get(tag.getString("t"))
    }
  }
}

class AnimaItem(id:Int) extends Item(id) {
  AnimaItem.item = this

  setUnlocalizedName("anima.itemUnknown")
  setCreativeTab(AnimaTab)
  setMaxDamage(0)
  setNoRepair()
  setMaxStackSize(64)
  setHasSubtypes(true)

  override def getUnlocalizedName(is: ItemStack): String = AnimaItem.lookUpItemStack(is).map {
    subItem => subItem.getUnlocalizedName(is)
  }.getOrElse("anima.itemUnknown")
}

trait AnimaSubItem {
  AnimaItem.put(name, this)

  def name:String

  def createItemStack:ItemStack = {
    val is = new ItemStack(AnimaItem.item)
    val nbt = new NBTTagCompound
    is.setTagCompound(nbt)
    nbt.setString("t", name)
    decorateNBT(nbt)
    is
  }

  def getUnlocalizedName(is:ItemStack) = "anima." + name

  def decorateNBT(tag: NBTTagCompound) {}

}