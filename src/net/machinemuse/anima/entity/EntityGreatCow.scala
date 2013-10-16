package net.machinemuse.anima.entity

import net.minecraft.world.World

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:29 PM, 10/15/13
 */
class EntityGreatCow(world: World) extends AnimaEntity(world: World) {
  def this(world: World, x: Int, y: Int, z: Int) {
    this(world)
    this.posX = x
    this.posY = y
    this.posZ = z
  }
}
