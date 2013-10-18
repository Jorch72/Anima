package net.machinemuse.anima.spirit

import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import net.machinemuse.anima.entity.{EntityGreatOcelot, EntityGreatSpirit, EntityGreatCow}
import net.minecraft.util.{Icon, ChatMessageComponent, AxisAlignedBB, MathHelper}
import net.minecraft.entity.item.EntityItem
import net.machinemuse.numina.general.MuseLogger
import net.minecraft.item.{ItemStack, Item}
import net.minecraft.block.Block
import net.machinemuse.anima.block.BlockCowTotem
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.potion.{Potion, PotionEffect}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:18 PM, 10/16/13
 */
object GreatOcelot extends GreatSpirit {

  def summon(world: World, player: EntityPlayer) {
    summon(world, player.posX.toInt, player.posY.toInt, player.posZ.toInt)
  }

  def summon(world: World, x: Int, y: Int, z: Int) {
    if (!world.isRemote) {
      val entity = new EntityGreatOcelot(world)
      entity.setLocationAndAngles(x, y + entity.yOffset, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat * 360.0F), 0.0F)
      entity.rotationYawHead = entity.rotationYaw
      entity.renderYawOffset = entity.rotationYaw
      world.spawnEntityInWorld(entity)
      val effectRadius = 50
      val effectArea = AxisAlignedBB.getBoundingBox(
        x - effectRadius, y - effectRadius, z - effectRadius,
        x + effectRadius, y + effectRadius, z + effectRadius
      )
      import scala.collection.JavaConverters._
      val entities = world.getEntitiesWithinAABBExcludingEntity(entity, effectArea).asScala
      for (entity <- entities) {
        entity match {
          case player: EntityPlayer => if (GreatOcelot.getFavour(player) >= 50) player.addPotionEffect(new PotionEffect(Potion.jump.getId, 2000))
          case spirit: EntityGreatSpirit => spirit.setDead()
          case _ =>
        }
      }
    }
  }


  def receiveOffering(player: EntityPlayer, entityitem: EntityItem, cow: EntityGreatSpirit): Boolean = {
    val stack = entityitem.getEntityItem
    MuseLogger.logDebug("Testing offering of" + stack)
    if (stack.itemID == Item.wheat.itemID) {
      GreatCow.addFavour(player, stack.stackSize)
      player.sendChatToPlayer(ChatMessageComponent.createFromText("The great cow seems pleased with your offering and gives you a hearty MOO." + getFavour(player).toString))
      true
    } else if (stack.itemID == Block.wood.blockID && getFavour(player) > 50) {
      addFavour(player, -50)
      stack.stackSize = stack.stackSize - 1

      val totem: EntityItem = player.dropPlayerItemWithRandomChoice(new ItemStack(BlockCowTotem), false)
      player.joinEntityItemWithWorld(totem)
      player.sendChatToPlayer(ChatMessageComponent.createFromText("The great cow sees fit to bestow you with a totem of her mighty milkiness."))
      stack.stackSize == 0
    } else {
      false
    }
  }

  def getIcon: Icon = icon

  var icon: Icon = null

  def registerIcons(register: IconRegister): Unit = {
    icon = register.registerIcon("anima:greatocelot")
  }

  val name = "greatocelot"
}
