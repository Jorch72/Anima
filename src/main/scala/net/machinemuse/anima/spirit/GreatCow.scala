package net.machinemuse.anima.spirit

import net.minecraft.world.World
import net.machinemuse.anima.entity.{EntityGreatSpirit, EntityGreatCow}
import net.minecraft.util.{ChatComponentText, MathHelper, IIcon, AxisAlignedBB}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.item.EntityItem
import net.minecraft.block.Block
import net.minecraft.item.{Item, ItemStack}
import net.machinemuse.anima.block.BlockCowTotem
import net.machinemuse.numina.general.MuseLogger


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 10:06 PM, 10/15/13
 */
object GreatCow extends GreatSpirit {
  def summon(world: World, player: EntityPlayer) {
    summon(world, player.posX.toInt, player.posY.toInt, player.posZ.toInt)
  }

  def summon(world: World, x: Int, y: Int, z: Int) {
    if (!world.isRemote) {
      val greatCowEntity = new EntityGreatCow(world)
      greatCowEntity.setLocationAndAngles(x, y + greatCowEntity.yOffset, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat * 360.0F), 0.0F)
      greatCowEntity.rotationYawHead = greatCowEntity.rotationYaw
      greatCowEntity.renderYawOffset = greatCowEntity.rotationYaw
      world.spawnEntityInWorld(greatCowEntity)
      val effectRadius = 50
      val effectArea = AxisAlignedBB.getBoundingBox(
        x - effectRadius, y - effectRadius, z - effectRadius,
        x + effectRadius, y + effectRadius, z + effectRadius
      )
      import scala.collection.JavaConverters._
      val entities = world.getEntitiesWithinAABBExcludingEntity(greatCowEntity, effectArea).asScala
      for (entity <- entities) {
        entity match {
          case player: EntityPlayer => if (GreatCow.getFavour(player) >= 50) player.heal(10)
          case spirit: EntityGreatSpirit => spirit.setDead()
          case _ =>
        }
      }
    }
  }


  def receiveOffering(player: EntityPlayer, entityitem: EntityItem, cow: EntityGreatSpirit): Boolean = {
    val stack = entityitem.getEntityItem
    MuseLogger.logDebug("Testing offering of" + stack)
    if (stack.getItem == Item.itemRegistry.getObject("wheat")) {
      GreatCow.addFavour(player, stack.stackSize)
      player.addChatComponentMessage(new ChatComponentText("The great cow seems pleased with your offering and gives you a hearty MOO." + getFavour(player).toString))
      true
    } else if (stack.getItem == Item.itemRegistry.getObject("wood") && getFavour(player) > 50) {
      addFavour(player, -50)
      stack.stackSize = stack.stackSize - 1

      val totem: EntityItem = player.dropPlayerItemWithRandomChoice(new ItemStack(BlockCowTotem), false)
      player.joinEntityItemWithWorld(totem)
      player.addChatComponentMessage(new ChatComponentText("The great cow sees fit to bestow you with a totem of her mighty milkiness."))
      stack.stackSize == 0
    } else {
      false
    }
  }

  def getIcon: IIcon = greatcowIcon

  var greatcowIcon: IIcon = null

  def registerIcons(register: IIconRegister): Unit = {
    greatcowIcon = register.registerIcon("anima:greatcow")
  }

  val name = "greatcow"
}
