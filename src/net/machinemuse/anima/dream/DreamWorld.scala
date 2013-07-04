package net.machinemuse.anima.dream

import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.ForgeDirection
import net.minecraft.util.Vec3Pool
import net.minecraft.world.biome.BiomeGenBase
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:31 PM, 7/3/13
 */
class DreamWorld extends IBlockAccess {
  def getBlockId(i: Int, j: Int, k: Int): Int = ???

  def getBlockTileEntity(i: Int, j: Int, k: Int): TileEntity = ???

  def getLightBrightnessForSkyBlocks(i: Int, j: Int, k: Int, l: Int): Int = ???

  def getBlockMetadata(i: Int, j: Int, k: Int): Int = ???

  def getBrightness(i: Int, j: Int, k: Int, l: Int): Float = ???

  def getLightBrightness(i: Int, j: Int, k: Int): Float = ???

  def getBlockMaterial(i: Int, j: Int, k: Int): Material = ???

  def isBlockOpaqueCube(i: Int, j: Int, k: Int): Boolean = ???

  def isBlockNormalCube(i: Int, j: Int, k: Int): Boolean = ???

  def isAirBlock(i: Int, j: Int, k: Int): Boolean = ???

  def getBiomeGenForCoords(i: Int, j: Int): BiomeGenBase = ???

  def getHeight: Int = ???

  def extendedLevelsInChunkCache(): Boolean = ???

  def doesBlockHaveSolidTopSurface(i: Int, j: Int, k: Int): Boolean = ???

  def getWorldVec3Pool: Vec3Pool = ???

  def isBlockProvidingPowerTo(i: Int, j: Int, k: Int, l: Int): Int = ???

  def isBlockSolidOnSide(x: Int, y: Int, z: Int, side: ForgeDirection, _default: Boolean): Boolean = ???
}
