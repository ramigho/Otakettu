package o1.otakettu

import scala.util.Random

// Class LoudPeople represents loud teekkaris which are partying somewhere in Otaniemi.
// Otafox and teekkaris have had encounters before, and they weren't so pleasant for the fox.
// That's why Otafox is scared of teekkaris and escapes from the location where they encounter.

class LoudPeople(val areas: Vector[Area]) {

  private val currentLocation = areas(randomGenerator)


  private def randomGenerator = Random.nextInt(areas.size)


  def location = this.currentLocation


  // Returns random, but defined escape route from the location of encounter.
  def escapeRoutes = {
    var destination = this.location.neighbor(areas(randomGenerator).description)

    while (destination.isEmpty || destination.get == this.location){
      destination = this.location.neighbor(areas(randomGenerator).description)
    }

    destination.get
  }


  // Returns escape routes from the location where Otafox encounter with teekkaris (LoudPeople).
  def happening() = {
    escapeRoutes.name
  }


  override def toString = "Teekkarilauma on ruudussa " + this.location.name


}