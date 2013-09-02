package net.machinemuse.anima.plants.behaviour

import net.machinemuse.anima.plants.PlantPart


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:04 PM, 6/28/13
 */
abstract class BreakBehaviour {

}

case class Break() extends BreakBehaviour {

}

case class Regress(regressionState: PlantPart) extends BreakBehaviour {

}