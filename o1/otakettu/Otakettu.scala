package o1.otakettu


// OtaFox (in finnish Otakettu, hence the class's name) is the main character of the game controlled by the player.
// OtaFox is a cute little fox living in Otaniemi. OtaFox is chasing OtaRabbit.

class Otakettu(val startingArea: Area, val chances: Vector[(LoudPeople, MagicMushroom, Otakotka)], val rabbit: Otajanis) {

  private var currentLocation = startingArea
  private var quitCommandGiven = false


  // Determines if the player has indicated a desire to quit the game.
  def hasQuit = this.quitCommandGiven


  // Returns the current location of the player.
  def location = this.currentLocation


  // Attempts to move the player in the given direction. This is successful if there
  // is an exit from the player's current location towards the direction name. Returns
  // a description of the result: "You go DIRECTION." or "You can't go DIRECTION."
  //
  // If destination is defined, checks whether there are chances (magic mushroom, loud people) in the same
  // area. If chance occurs, chance is executed and return's description is adapted.
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    var result = "Et voi mennä paikkaan " + direction.capitalize + "."

    if (destination.isDefined) {
      result = "Siirryit paikkaan " + direction.capitalize + ".\n"

      if (chances.map(_._2.location).head == this.location) {        // Samassa ruudussa kuin sieni.
        result = "Siirryit paikkaan " + direction.capitalize + ".\n\n" + chances.map(_._2.happening()).head
      }

      else if (chances.map(_._1.location).head == this.location) {   // Samassa ruudussa kuin teekkarilauma
        val previousDestination = this.location.neighbor(direction).getOrElse(this.currentLocation)
        val newDestination = previousDestination.neighbor(chances.map(_._1.escapeRoutes).head.description)
        this.currentLocation = newDestination.getOrElse(this.currentLocation)
        result = "\nTörmäsit meluavaan ja uhkaavaan teekkarilaumaan paikassa "+previousDestination.name+"!\n"+
                  "Säikähdit ja juoksit äkkiä paikkaan " + this.currentLocation.name + "."
      }

      else if (chances.map(_._3.location).head == this.location) {
        if (!chances.map(_._3.isFound).head) {
          this.currentLocation = chances.map(_._3.happening()).head
          result = "Törmäsit vanhaan tuttuusi Otakotkaan. Hän lupautuu lennättämään sinut mihin tahansa paikkaan Otaniemessä"
        } else {
          result = "Siirryit paikkaan " + direction + "."
        }
      }

    }
    result
  }


  // Signals that the player wants to quit the game. Returns a description of what happened within
  // the game as a result (which is the empty string, in this case).
  def quit() = {
    this.quitCommandGiven = true
  }


  // Returns a brief description of the player's state, for debugging purposes.
  override def toString = "Now at: " + this.location.name

}