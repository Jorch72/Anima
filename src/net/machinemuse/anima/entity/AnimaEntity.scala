package net.machinemuse.anima.entity

import net.minecraft.entity.EntityLiving
import net.minecraft.world.World

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:35 PM, 7/2/13
 */
class AnimaEntity(world:World) extends EntityLiving(world) {
  def getMaxHealth: Int = 20

}

class AnimaEntityBase {

}

object Salamander extends AnimaEntityBase {

}