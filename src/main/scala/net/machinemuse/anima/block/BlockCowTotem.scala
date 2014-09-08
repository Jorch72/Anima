package net.machinemuse.anima.block

import net.machinemuse.anima.block.wood.WoodBlock
import net.machinemuse.anima.item.BlockIDManager
import cpw.mods.fml.common.registry.GameRegistry
import net.machinemuse.anima.plants.{PlantTileEntity, PlantItemBlock}
import net.machinemuse.anima.Anima

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:08 AM, 10/16/13
 */
object BlockCowTotem extends WoodBlock (BlockIDManager.getID("cowtotem")){
  setUnlocalizedName("cowtotem")
}
