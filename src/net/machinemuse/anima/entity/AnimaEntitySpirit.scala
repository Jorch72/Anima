package net.machinemuse.anima.entity

import net.minecraft.entity.player.EntityPlayer
import java.util.Random
import net.minecraft.block.Block
import net.minecraftforge.common.IPlantable

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:58 PM, 11/12/13
 */
object AnimaEntitySpirit {
  val random = new Random()
}

class AnimaEntityGrowthSpirit extends AnimaEntitySpirit {
  override def onEntityUpdate() {
    super.onEntityUpdate()
    val r = 10
    val x = (posX + r * 2 * (AnimaEntitySpirit.random.nextDouble - 0.5)).toInt
    val y = (posY + r * 2 * (AnimaEntitySpirit.random.nextDouble - 0.5)).toInt
    val z = (posZ + r * 2 * (AnimaEntitySpirit.random.nextDouble - 0.5)).toInt
    val block = Block.blocksList(worldObj.getBlockId(x, y, z))
    val odds = r * r * r * 8.0
    val bonusChance = 0.1
    val threshold = bonusChance * odds * 5.0 / 16384.0
    if (AnimaEntitySpirit.random.nextDouble < threshold && block.getTickRandomly && block.isInstanceOf[IPlantable]) {
      block.updateTick(worldObj, x, y, z, AnimaEntitySpirit.random)
    }
  }
}

trait AnimaEntitySpirit extends AnimaEntity {
  var summoner: Option[EntityPlayer] = None
  var happiness: Int = 0
}
