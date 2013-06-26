package net.machinemuse.anima.block.plants

import net.minecraft.util.Icon

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:02 PM, 6/18/13
 */
object PlantPart {
  val subtypes: Array[PlantPart] = Array(PlantedLotus)

  object PlantedLotus extends PlantPart("lotus.plantedSeed", "Lotus Seed", "lotusSeed") {}

  object LotusTuber {}

  object LotusLeaf {}

  object LotusBud {}

  object LotusBlossom {}

}

class PlantPart(val name: String, val localizedName: String, val icon: String) {
  var registeredIcon: Icon = null
}