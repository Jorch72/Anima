package net.machinemuse.anima.entity

import net.minecraft.world.World
import net.minecraft.entity.ai.EntityAIWander
import net.minecraft.block.Block
import net.minecraftforge.common.IPlantable
import net.machinemuse.numina.random.MuseRandom
import net.machinemuse.anima.block.TileEntityIncenseBurner
import net.minecraft.item.ItemStack
import net.machinemuse.numina.general.MuseLogger

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:58 PM, 11/12/13
 */
class AnimaEntityHarvestSprite(world: World) extends AnimaEntitySprite(world) {

  def this(world: World, tetherX: Int, tetherY: Int, tetherZ: Int) {
    this(world)
    this.tetherX = tetherX
    this.tetherY = tetherY
    this.tetherZ = tetherZ
  }

  def checkDeathConditions() {
    val check = world.getBlockTileEntity(tetherX, tetherY, tetherZ)
    world.getBlockTileEntity(tetherX, tetherY, tetherZ) match {
      case te: TileEntityIncenseBurner =>
        if (te.isBurning && te.hasIncense && isValidIncense(te.incense.get)) {
        } else {
          maybeDie()
        }
      case _ => maybeDie()
    }
  }

  def maybeDie() {
    if (MuseRandom.nextDouble < 0.01) {
      this.setDead()
    }
  }

  def moveRandomly() {
    motionX += (MuseRandom.nextDouble - 0.5) * 0.01 + (spotX - posX) * 0.002
    motionY += (MuseRandom.nextDouble - 0.5) * 0.01 + (spotY - posY) * 0.002
    motionZ += (MuseRandom.nextDouble - 0.5) * 0.01 + (spotZ - posZ) * 0.002
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
    if(sumsq > ratio) {
      motionX *= ratio / sumsq
      motionY *= ratio / sumsq
      motionZ *= ratio / sumsq
    }
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
      block.updateTick(worldObj, x, y, z, MuseRandom)
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

  def isValidIncense(stack: ItemStack): Boolean = true
}
