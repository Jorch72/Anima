package net.machinemuse.anima.spirit

import net.minecraft.entity.player.EntityPlayer
import scala.collection.mutable
import net.minecraft.util.IIcon
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.world.World

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:36 PM, 7/3/13
 */
trait GreatSpirit {
  val name: String

  def registerIcons(register: IIconRegister)

  def getIcon: IIcon

  val favourMapping = new mutable.HashMap[String, Int]

  def addFavour(player: EntityPlayer, favour: Int) {
    favourMapping.put(player.getCommandSenderName, getFavour(player) + favour)
  }

  def getFavour(player: EntityPlayer) = favourMapping.get(player.getCommandSenderName).getOrElse(0)

  def summon(world:World, player:EntityPlayer)
  // Mapping of player usernames to info on their relationship
}
