package net.machinemuse.anima.entity

import net.minecraft.world.World
import net.minecraft.entity.ai.EntityAIWander
import net.minecraft.block.Block
import net.minecraftforge.common.IPlantable
import net.machinemuse.numina.random.MuseRandom

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:58 PM, 11/12/13
 */
class AnimaEntityGrowthSpirit(world:World) extends AnimaEntitySpirit(world) {
  this.tasks.addTask(0, new EntityAIWander(this, 0.5))

  this.setSize(0.5f, 0.5f)

  override def onEntityUpdate() {
    super.onEntityUpdate()
    val r = 10
    val x = (posX + r * 2 * (MuseRandom.nextDouble - 0.5)).toInt
    val y = (posY + r * 2 * (MuseRandom.nextDouble - 0.5)).toInt
    val z = (posZ + r * 2 * (MuseRandom.nextDouble - 0.5)).toInt
    val block = Block.blocksList(worldObj.getBlockId(x, y, z))
    val odds = r * r * r * 8.0
    val bonusChance = 0.1
    val threshold = bonusChance * odds * 5.0 / 16384.0
    if (block != null && MuseRandom.nextDouble < threshold && block.getTickRandomly && block.isInstanceOf[IPlantable]) {
      block.updateTick(worldObj, x, y, z, MuseRandom.random)
    }

    this.worldObj.spawnParticle("flame", this.posX + Math.random * 0.1, this.posY + Math.random * 0.1, this.posZ + Math.random * 0.1, 0.0D, 0.0D, 0.0D)

  }

  /**
   * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
   * prevent them from trampling crops
   */
  protected override def canTriggerWalking: Boolean = false

  /**
   * Return whether this entity should NOT trigger a pressure plate or a tripwire.
   */
  override def doesEntityNotTriggerPressurePlate: Boolean = true

}
