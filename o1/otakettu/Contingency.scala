package o1.otakettu

import scala.util.Random

class Contingency(val name: String, val areas: Vector[Area]) {

  var startingArea = areas(randomGenerator)
  private var currentLocation = startingArea


  def location = this.currentLocation


  private def randomGenerator = Random.nextInt(areas.size)

  def check(location: Area) = {

    if (location == this.location){


    }


  }


  /** Sieni:
    *  Otakettu löytää taikasienen, jonka avulla se kykenee haistamaan Otajäniksen parin vuoron ajan.
    *  Eli Otajäniksen kuvake näkyy poikkeuksellisesti kartalla seuraavan parin vuoron ajan.  */

  if (name.equals("Sieni")) {









  }




}
