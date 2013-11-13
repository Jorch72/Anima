package net.machinemuse.anima.entity

import net.minecraft.entity.EntityCreature
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World


class AnimaEntitySpirit(world: World) extends EntityCreature(world) {
  var summoner: Option[EntityPlayer] = None
  var happiness: Int = 0
}
