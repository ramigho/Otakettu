package o1.otakettu

import scala.collection.mutable.Map



  class Area(var name: String, var description: String) {

    val neighbors = Map[String, Area]()


    // MIHIN SUUNTAAN VOIDAAN LIIKKUA
    def neighbor(direction: String) = this.neighbors.get(direction)


    // SELVITÄ NÄIDEN TOIMINTA JA ROOLI
    def setNeighbor(direction: String, neighbor: Area) = {
      this.neighbors += direction -> neighbor
    }

    def setNeighbors(exits: Vector[(String, Area)]) = {
      this.neighbors ++= exits
    }


    def fullDescription = {
        "Sijaintisi: " + this.name + "\n\nVoit liikkua sijaintiin:\n - " + this.neighbors.keys.mkString("\n - ")
    }


}
