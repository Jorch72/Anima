package net.machinemuse.anima.plants.parts

import net.machinemuse.anima.block.plants.{PlantRenderCross, PlantRenderHash, PlantPart}
import net.machinemuse.anima.plants.behaviour.{GrowUp, Regress, GrowInPlace}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:44 PM, 7/3/13
 */
object WoadFlowers
  extends PlantPart(
    name = "woadFlowers",
    render = PlantRenderHash("woadflowers")
  )

object WoadLeavesFull
  extends PlantPart(
    name = "woadLeavesFull",
    render = PlantRenderCross("woadleavesfull"),
    growth = GrowUp(WoadFlowers),
    break = Regress(WoadLeavesTrimmed)
  )

object WoadLeavesTrimmed
  extends PlantPart(
    name = "woadLeavesTrimmed",
    render = PlantRenderCross("woadleavestrimmed"),
    growth = GrowInPlace(WoadLeavesFull)
  )

object WoadSprout
  extends PlantPart(
    name = "woadSprout",
    render = PlantRenderCross("woadsprout"),
    growth = GrowInPlace(WoadLeavesTrimmed)
  )

