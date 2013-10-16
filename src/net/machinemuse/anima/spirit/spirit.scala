package net.machinemuse.anima.spirit

import net.minecraft.entity.player.EntityPlayer
import scala.collection.mutable
import net.minecraft.util.Icon
import net.minecraft.client.renderer.texture.IconRegister

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:36 PM, 7/3/13
 */
trait Spirit {
  val name: String

  def registerIcons(register: IconRegister)

  def getIcon: Icon

  val favourMapping = new mutable.HashMap[String, Int]

  def addFavour(favour: Int, player: EntityPlayer) {
    favourMapping.put(player.getCommandSenderName, getFavour(player) + favour)
  }

  def getFavour(player: EntityPlayer) = favourMapping.get(player.getCommandSenderName).getOrElse(0)

  // Mapping of player usernames to info on their relationship
}
