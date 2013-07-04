package net.machinemuse.anima.plants.parts

import net.machinemuse.anima.block.plants.{PlantRenderCross, PlantPart}
import net.machinemuse.anima.plants.behaviour.{DropItem, DropSelf, GrowInPlace}

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:48 PM, 7/3/13
 */
object SageCuttings
  extends PlantPart(
    name = "sageCuttings",
    render = PlantRenderCross("sageCuttings"),
    growth = GrowInPlace(SageGrowing),
    drop = DropSelf()
  )

object SageGrowing
  extends PlantPart(
    name = "sageGrowing",
    render = PlantRenderCross("sageGrowing"),
    growth = GrowInPlace(SageGrown),
    drop = DropItem(SageCuttings.itemStack(2))
  )

object SageGrown
  extends PlantPart(
    name = "sageGrown",
    render = PlantRenderCross("sageGrown"),
    drop = DropItem(SageCuttings.itemStack(3))
  )

