package net.machinemuse.anima.entity

import net.minecraft.entity.passive._
import net.minecraft.world.World
import net.minecraft.entity.{Entity, SharedMonsterAttributes, EntityAgeable}
import net.minecraft.entity.ai.EntityAIWatchClosest
import net.minecraft.entity.player.EntityPlayer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:58 AM, 10/16/13
 */

class EntityGreatCow(world: World) extends EntityCow(world) with EntityGreatSpirit

class EntityGreatOcelot(world: World) extends EntityOcelot(world) with EntityGreatSpirit

class EntityGreatPig(world: World) extends EntityPig(world) with EntityGreatSpirit

class EntityGreatChicken(world: World) extends EntityChicken(world) with EntityGreatSpirit

class EntityGreatSheep(world: World) extends EntitySheep(world) with EntityGreatSpirit

trait EntityGreatSpirit extends EntityAnimal {

  this.yOffset = 6.5f
  this.width = 4.5F
  this.height = 6.5F
  //  this.setSize(4.5F, 6.5F)
  this.boundingBox.expand(5, 5, 5)
  this.getNavigator.setAvoidsWater(true)

  this.tasks.addTask(0, new EntityAIWatchClosest(this, classOf[EntityPlayer], 60.0F))


  protected override def findPlayerToAttack(): Entity = {
    val list = this.worldObj.getEntitiesWithinAABB(classOf[EntityPlayer], this.boundingBox.expand(60.0, 60.0, 60.0))
    if (list.size > 0) {
      return list.get(0).asInstanceOf[EntityPlayer]
    }
    null
  }

  protected override def applyEntityAttributes() {
    super.applyEntityAttributes()
    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(10000.0D)
    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.2D)
  }

  override def onEntityUpdate() {
    this.yOffset = 6.5f
    this.width = 4.5F
    this.height = 6.5F
    if (lastActionTime > worldObj.getWorldTime) lastActionTime -= 24000L

    if (worldObj.getWorldTime - lastActionTime > inactivityTime) this.setDead()
    super.onEntityUpdate()
  }

  val inactivityTime = 20 * 60
  var lastActionTime = worldObj.getWorldTime

  override def canMateWith(par1EntityAnimal: EntityAnimal): Boolean = false

  override def createChild(entityageable: EntityAgeable): EntityAgeable = null
}
