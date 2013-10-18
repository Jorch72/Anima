package net.machinemuse.anima.entity

import net.minecraft.entity.{EntityAgeable, EntityLiving}
import net.minecraft.world.World
import net.minecraft.entity.passive.EntityAnimal

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:35 PM, 7/2/13
 */
class AnimaEntity(world: World) extends EntityAnimal(world) {
  def createChild(entityageable: EntityAgeable): EntityAgeable = new EntityGreatCow(entityageable.worldObj)
}

trait AnimaEntityBase {

}

object Salamander extends AnimaEntityBase {

}

object Sylph extends AnimaEntityBase {

}

object Undine extends AnimaEntityBase {

}

object Gnome extends AnimaEntityBase {

}