package o1.otakettu

import scala.util.Random



// Magic Mushroom, which allows OtaFox (Otakettu) to smell Otarabbit for couple of turns, if eaten.
// After eating the mushroom, OtaFox sees the OtaRabbit on the map for a couple turns.

class MagicMushroom(val areas: Vector[Area]) {

  private val currentLocation = areas(randomGenerator)
  var isFound = false
  var isEaten = false


  private def randomGenerator = Random.nextInt(areas.size)


  def location = this.currentLocation


  // When mushroom is found and eaten, mushroom's effects take place and OtaFox sees the OtaRabbit on the map.
  def happening() = {

    var result = "Taikasieni on poimittu."
    if (!this.isFound) {
    result = "Löysit taikasienen! Voit syömällä sen nähdä Otajäniksen sijainnin kartalla seuraavan parin vuoron ajan."
    this.isFound = true
    }
    result
  }


  def eat() = {
    var result = "Sinulla ei ole mitään syötävää."
    if (this.isFound && !this.isEaten) {
      this.isEaten = true
      result = "Söit taikasienen. Tunnet, kuinka hajuaistisi terävöityy. Voit haistaa jäniksen!"
    }
    result
  }


  override def toString = "Taikasieni on ruudussa " + this.location.name

}
