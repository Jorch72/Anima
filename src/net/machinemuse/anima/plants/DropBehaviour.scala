package net.machinemuse.anima.plants

import net.minecraft.item.ItemStack

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:07 PM, 6/28/13
 */
abstract class DropBehaviour {

}

case class DropNothing() extends DropBehaviour

case class DropSelf() extends DropBehaviour

case class DropItem(drop: ItemStack) extends DropBehaviour