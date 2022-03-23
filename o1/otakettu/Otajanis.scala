package o1.otakettu

import scala.util.Random


// Class Otajanis represents OtaRabbit (in finnish Otajänis, hence the class's name), which is being hunted by the fox.
// OtaRabbit only know's its location. It's next move is random in some direction where it can go.

class Otajanis(val areas: Vector[Area], val canSpawn: Vector[Area]) {

  var everyThird = 0
  private var currentLocation = canSpawn(Random.nextInt(4))


  def location = this.currentLocation


  private def randomGenerator = Random.nextInt(areas.size)


  def move() = {
    var destination = this.location.neighbor(areas(randomGenerator).description)

    while (destination.isEmpty) {
      destination = this.location.neighbor(areas(randomGenerator).description)
    }

    this.everyThird += 1
    this.currentLocation = destination.getOrElse(this.currentLocation)
  }



  override def toString = "Otajänis is currently at " + this.currentLocation.name


}