package net.machinemuse.anima.dream

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockAccess
import net.minecraft.world.biome.BiomeGenBase
import net.minecraftforge.common.util.ForgeDirection

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:31 PM, 7/3/13
 */
class DreamWorld extends IBlockAccess {

  override def getBlock(p_147439_1_ : Int, p_147439_2_ : Int, p_147439_3_ : Int): Block = ???

  override def isSideSolid(x: Int, y: Int, z: Int, side: ForgeDirection, _default: Boolean): Boolean = ???

  override def getTileEntity(p_147438_1_ : Int, p_147438_2_ : Int, p_147438_3_ : Int): TileEntity = ???

  override def getLightBrightnessForSkyBlocks(p_72802_1_ : Int, p_72802_2_ : Int, p_72802_3_ : Int, p_72802_4_ : Int): Int = ???

  override def getHeight: Int = ???

  override def isBlockProvidingPowerTo(p_72879_1_ : Int, p_72879_2_ : Int, p_72879_3_ : Int, p_72879_4_ : Int): Int = ???

  override def extendedLevelsInChunkCache(): Boolean = ???

  override def isAirBlock(p_147437_1_ : Int, p_147437_2_ : Int, p_147437_3_ : Int): Boolean = ???

  override def getBiomeGenForCoords(p_72807_1_ : Int, p_72807_2_ : Int): BiomeGenBase = ???

  override def getBlockMetadata(p_72805_1_ : Int, p_72805_2_ : Int, p_72805_3_ : Int): Int = ???
}
