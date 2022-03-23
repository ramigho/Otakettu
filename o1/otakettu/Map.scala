package o1.otakettu

import o1._


// Represents the map of (Otaniemi,) the game. Knows locations of the characters that can be seen on the map.
// Uses pixel coordinates to place the characters on Area-objects around the map.

class Map(val otakettu: Otakettu, val otajanis: Otajanis, val areas: Vector[Area], val chances: Vector[(LoudPeople, MagicMushroom, Otakotka)]) {

  private val coords = coordsToPlaces()


  val karttaPohja = Pic("otakettukartta.jpg")
  val otakettuKuva = Pic("Otakettu.png").scaleBy(0.09)
  val otajänisKuva = Pic("Otajänis.png").scaleBy(0.4)
  val kotkanKuva = Pic("Otakotka.png").scaleBy(0.3)
  val voittoKuva = Pic("rabbit-eaten-by-fox.jpg").scaleBy(0.3)



  var showRabbit = 0
  def showMap() = {

    var defaultMap = karttaPohja.toImage.get


    if (chances.map(_._3.flying).head == otakettu.location) {
      defaultMap = kotkanKuva.onto(karttaPohja, coords(chances.map(_._3.spawn).head).addX(10)).toImage.get

    } else {
      defaultMap = otakettuKuva.onto(karttaPohja, coords(otakettu.location)).toImage.get

      if (otakettu.location == otajanis.location) {
        defaultMap = voittoKuva.onto(karttaPohja, coords(otakettu.location)).toImage.get
      }

      else if (otajanis.everyThird % 3 == 0){
        defaultMap = otajänisKuva.onto(otakettuKuva.onto(karttaPohja, coords(otakettu.location)), coords(otajanis.location).addY(-14)).toImage.get
      }

      else if (chances.map(_._2.isEaten).head && showRabbit <= 2){
        defaultMap = otajänisKuva.onto(otakettuKuva.onto(karttaPohja, coords(otakettu.location)), coords(otajanis.location).addY(-14)).toImage.get
        showRabbit += 1
      }
    }

    defaultMap
  }


  def coordsToPlaces() = {
    var koords = Map[Area, Pos]()
    koords += areas(0) -> Pos(657, 67)
    koords += areas(1) -> Pos(753, 263)
    koords += areas(2) -> Pos(617, 216)
    koords += areas(3) -> Pos(432, 224)
    koords += areas(4) -> Pos(636, 347)
    koords += areas(5) -> Pos(720, 458)
    koords += areas(6) -> Pos(567, 535)
    koords += areas(7) -> Pos(668, 694)
    koords += areas(8) -> Pos(469, 637)
    koords += areas(9) -> Pos(310, 706)
    koords += areas(10) -> Pos(300, 547)
    koords += areas(11) -> Pos(45, 610)
    koords += areas(12) -> Pos(225, 406)
    koords += areas(13) -> Pos(434, 459)
    koords
  }

}