package net.machinemuse.anima.entity

import net.minecraft.entity.EntityCreature
import net.minecraft.world.World
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound


abstract class AnimaEntitySprite(world: World) extends EntityCreature(world) {


  def isValidIncense(stack: ItemStack): Boolean

  override def getCanSpawnHere = {
    worldObj.isAirBlock(posX.toInt, posY.toInt, posZ.toInt)

  }

  this.setSize(0.5f, 0.5f)
  var tetherX = 0
  var tetherY = 0
  var tetherZ = 0

  var spotX = 0.0
  var spotY = 0.0
  var spotZ = 0.0

  def setSpot(x: Double, y: Double, z: Double) = {
    spotX = x
    spotY = y
    spotZ = z
    setPositionAndUpdate(x,y,z)
    this
  }

  def this(world: World, tetherX: Int, tetherY: Int, tetherZ: Int) {
    this(world)
    this.tetherX = tetherX
    this.tetherY = tetherY
    this.tetherZ = tetherZ
  }

  override def onUpdate() {
    if(!world.isRemote) {
      tickNearbyBlocks()
      moveRandomly()
      checkDeathConditions()
    }
  }
  def tickNearbyBlocks()
  def moveRandomly()
  def checkDeathConditions()

  override def writeEntityToNBT(nbt: NBTTagCompound) {
    super.writeEntityToNBT(nbt)
    nbt.setIntArray("tetherspot", Array(tetherX, tetherY, tetherZ, spotX.toInt, spotY.toInt, spotZ.toInt))
  }
  override def readEntityFromNBT(nbt: NBTTagCompound) {
    super.readEntityFromNBT(nbt)
    if(nbt.hasKey("tetherspot")) {
      val tetherspot = nbt.getIntArray("tetherspot")
      tetherX = tetherspot(0)
      tetherY = tetherspot(1)
      tetherZ = tetherspot(2)
      spotX = tetherspot(3)
      spotY = tetherspot(4)
      spotZ = tetherspot(5)
    }
  }
}
