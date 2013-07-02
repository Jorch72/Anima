package net.machinemuse.anima.plants

import net.machinemuse.anima.block.plants.PlantPart


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