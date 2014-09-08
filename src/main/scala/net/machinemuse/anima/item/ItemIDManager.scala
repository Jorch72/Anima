package net.machinemuse.anima.item

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 11:47 PM, 10/15/13
 */
object ItemIDManager {
  var id = 12345
  def getID(unlocalizedname: String): Int = {
    id = id + 1
    id
  }
}

object BlockIDManager {
  var id = 1234
  def getID(unlocalizedname: String): Int = {
    id = id + 1
    id
  }
}