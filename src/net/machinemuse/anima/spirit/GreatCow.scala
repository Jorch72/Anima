package net.machinemuse.anima.spirit

import net.minecraft.world.World
import net.machinemuse.anima.entity.EntityGreatCow
import net.minecraft.util.{Icon, AxisAlignedBB}
import net.machinemuse.numina.scala.OptionCast
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.renderer.texture.IconRegister


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:06 PM, 10/15/13
 */
object GreatCow extends Spirit {
  def summon(world: World, x: Int, y: Int, z: Int) {
    val greatCowEntity = new EntityGreatCow(world, x, y, z)
    world.spawnEntityInWorld(greatCowEntity)
    val effectRadius = 50
    val effectArea = AxisAlignedBB.getBoundingBox(
      x - effectRadius, y - effectRadius, z - effectRadius,
      x + effectRadius, y + effectRadius, z + effectRadius
    )
    import scala.collection.JavaConverters._
    val entities = world.getEntitiesWithinAABBExcludingEntity(greatCowEntity, effectArea).asScala
    for (entity <- entities) {
      OptionCast[EntityPlayer](entity).map(
        player => if (GreatCow.getFavour(player) >= 50) {
          player.heal(10)
        }
      )
    }
  }

  def getIcon: Icon = greatcowIcon

  var greatcowIcon:Icon = null

  def registerIcons(register: IconRegister): Unit = {
    greatcowIcon = register.registerIcon("anima:greatcow")
  }

  val name = "greatcow"
}
