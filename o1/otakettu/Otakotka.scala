package o1.otakettu

import scala.util.Random

class Otakotka(val areas: Vector[Area], val canSpawn: Vector[Area], val flying: Area) {

  var spawn = canSpawn(Random.nextInt(3))
  private var currentLocation = spawn
  var isFound = false


  def location = this.currentLocation

  private def randomGenerator = Random.nextInt(areas.size)


  def happening() ={
    this.isFound = true
    this.currentLocation = flying
    currentLocation
  }


  override def toString = "Otakotka sijainnissa " + this.location.name

}
