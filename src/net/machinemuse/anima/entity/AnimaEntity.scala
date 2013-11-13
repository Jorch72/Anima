package net.machinemuse.anima.entity

import net.minecraft.entity.{EntityAgeable, EntityLiving}
import net.minecraft.world.World
import net.minecraft.entity.passive.EntityAnimal

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:35 PM, 7/2/13
 */
abstract class AnimaEntity(world: World) extends EntityLiving(world) {
  def createChild(entityageable: EntityAgeable): EntityAgeable = null
}