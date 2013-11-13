package net.machinemuse.anima.entity

import net.minecraft.world.World
import net.minecraft.entity.ai.EntityAIWander
import net.minecraft.block.Block
import net.minecraftforge.common.IPlantable
import net.machinemuse.numina.random.MuseRandom
import net.machinemuse.numina.scala.OptionCast
import net.machinemuse.anima.block.TileEntityIncenseBurner
import net.minecraft.item.ItemStack
import net.machinemuse.anima.item.Incense

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:58 PM, 11/12/13
 */
class AnimaEntityHarvestSprite(world: World) extends AnimaEntitySprite(world) {
  this.tasks.addTask(0, new EntityAIWander(this, 0.5))

  this.setSize(0.5f, 0.5f)

  var tetherX = 0
  var tetherY = 0
  var tetherZ = 0

  override def onUpdate() {
    tickNearbyBlocks()
    moveRandomly()
    checkDeathConditions()
  }

  def checkDeathConditions() {
    world.getBlockTileEntity(tetherX, tetherY, tetherZ) match {
      case te:TileEntityIncenseBurner =>
        if(te.isBurning && te.hasIncense && isValidIncense(te.incense.get)) {
          // stay alive?
        } else {
          maybeDie()
        }
      case _ => maybeDie()
    }
  }

  def maybeDie() {
    if(MuseRandom.nextDouble < 0.01) {
      this.kill()
    }
  }

  def isValidIncense(stack:ItemStack) = true

  def moveRandomly() {
    motionX += (MuseRandom.nextDouble - 0.5) * 0.001 + (tetherX - posX) * 0.0002
    motionY += (MuseRandom.nextDouble - 0.5) * 0.001 + (tetherY - posY) * 0.0002
    motionZ += (MuseRandom.nextDouble - 0.5) * 0.001 + (tetherZ - posZ) * 0.0002
    normalizeMotion(0.01)
    setPositionAndUpdate(
      posX + motionX,
      posY + motionY,
      posZ + motionZ)
  }

  def normalizeMotion(ratio: Double) {
    val sumsq = Math.sqrt(
      motionX * motionX +
      motionY * motionY +
      motionZ * motionZ)
    motionX *= ratio / sumsq
    motionY *= ratio / sumsq
    motionZ *= ratio / sumsq
  }

  def tickNearbyBlocks() {
    val r = 8.0
    val x = (posX + r * (MuseRandom.nextDouble * 2.0 - 1.0)).toInt
    val y = (posY + r * (MuseRandom.nextDouble * 2.0 - 1.0)).toInt
    val z = (posZ + r * (MuseRandom.nextDouble * 2.0 - 1.0)).toInt
    val block = Block.blocksList(worldObj.getBlockId(x, y, z))
    val odds = r * r * r * 8.0
    val bonusChance = 100.0
    val threshold = bonusChance * odds * 5.0 / 16384.0
    if (block != null && block.getTickRandomly && block.isInstanceOf[IPlantable]) {
      block.updateTick(worldObj, x, y, z, MuseRandom.random)
    }
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
