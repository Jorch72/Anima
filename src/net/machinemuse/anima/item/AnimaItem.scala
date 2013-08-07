package net.machinemuse.anima.item

import net.minecraft.item.{ItemStack, Item}
import net.machinemuse.utils.MuseRegistry
import net.minecraft.nbt.NBTTagCompound
import net.machinemuse.anima.AnimaTab
import net.minecraft.creativetab.CreativeTabs
import java.util
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.util.Icon
import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.entity.player.EntityPlayer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:29 AM, 6/18/13
 */
object AnimaItem extends MuseRegistry[AnimaSubItem] {
  var item: AnimaItem = null

  def lookUpItemStack(is: ItemStack): Option[AnimaSubItem] = {
    Option(is.getTagCompound).flatMap {
      tag => get(tag.getString("t"))
    }
  }

  def init(id: Int) {
    item = new AnimaItem(id)
    addSubItem(DreamCatcher)
    addSubItem(Kettle)
    addSubItem(Basket)
  }

  def addSubItem(subItem: AnimaSubItem) = put(subItem.name, subItem)

}

class AnimaItem(id: Int) extends Item(id) {
  setUnlocalizedName("anima.itemUnknown")
  setCreativeTab(AnimaTab)
  setMaxDamage(0)
  setNoRepair()
  setMaxStackSize(64)
  setHasSubtypes(true)

  override def getUnlocalizedName(is: ItemStack): String = AnimaItem.lookUpItemStack(is).map {
    subItem => subItem.getUnlocalizedName(is)
  }.getOrElse("anima.itemUnknown")

  override def getSubItems(itemID: Int, creativeTab: CreativeTabs, listToAddTo: util.List[_]) {
    val list = listToAddTo.asInstanceOf[util.List[ItemStack]]
    AnimaItem.elems foreach (e => list.add(e.createItemStack))
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(register: IconRegister) {
    AnimaItem.elems foreach (e => e.registerIcon(register))
  }


  override def getIconFromDamage(par1: Int): Icon = DreamCatcher.icon

  override def getIcon(stack: ItemStack, renderPass: Int, player: EntityPlayer, usingItem: ItemStack, useRemaining: Int): Icon = AnimaItem.lookUpItemStack(stack).map(i => i.icon).getOrElse(null)

  override def getIcon(stack: ItemStack, pass: Int): Icon = AnimaItem.lookUpItemStack(stack).map(i => i.icon).getOrElse(null)
}

trait AnimaSubItem {
  AnimaItem.put(name, this)

  def name: String

  def createItemStack: ItemStack = {
    val is = new ItemStack(AnimaItem.item)
    val nbt = new NBTTagCompound
    is.setTagCompound(nbt)
    nbt.setString("t", name)
    decorateNBT(nbt)
    is
  }

  def iconFile: String

  var icon: Icon = null

  @SideOnly(Side.CLIENT)
  def registerIcon(registry: IconRegister) {
    icon = registry.registerIcon("Anima:" + iconFile)
  }

  def getUnlocalizedName(is: ItemStack) = "anima." + name

  def decorateNBT(tag: NBTTagCompound) {}

  def getDescription = "anima." + name + ".description"

}