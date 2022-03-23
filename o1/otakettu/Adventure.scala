package o1.otakettu

import o1.Buffer
import o1._

class Adventure {


  val title = "Otakettu"
  private var areasBuffer = Buffer[Area]()



  // Creates world objects and adds Area-obects to a Buffer
  private val niemenkarki          = new Area("Niemenkärki", "niemenkärki") //
  areasBuffer += niemenkarki
  private val jämeränranta         = new Area("Jämeränranta", "jämeränranta" )
  areasBuffer += jämeränranta
  private val smt                  = new Area("SMT", "smt")
  areasBuffer += smt
  private val yths                 = new Area("YTHS", "yths")
  areasBuffer += yths
  private val kappelipuisto        = new Area("Kappelipuisto", "kappelipuisto")
  areasBuffer += kappelipuisto
  private val rantasauna           = new Area("Rantasauna", "rantasauna")
  areasBuffer += rantasauna
  private val jmt1                 = new Area("JMT1", "jmt1")
  areasBuffer += jmt1
  private val otaranta             = new Area("Otaranta", "otaranta")
  areasBuffer += otaranta
  private val täffä                = new Area("Täffä", "täffä") //
  areasBuffer += täffä
  private val alvarinaukio         = new Area("Alvarinaukio", "alvarinaukio") //
  areasBuffer += alvarinaukio
  private val kandikeskus          = new Area("Kandikeskus", "kandikeskus")
  areasBuffer += kandikeskus
  private val kauppakorkeakoulu    = new Area("Kauppakorkeakoulu", "kauppakorkeakoulu") //
  areasBuffer += kauppakorkeakoulu
  private val maarintalo           = new Area("Maarintalo", "maarintalo")
  areasBuffer += maarintalo
  private val ossinlampi           = new Area("Ossinlampi", "ossinlampi")
  areasBuffer += ossinlampi
  private val fly                   = new Area("fly", "fly")


  // Creates the world: ties Area-objects to each other if necessary.
  niemenkarki.setNeighbors(Vector("yths" -> yths, "jämeränranta" -> jämeränranta))
  jämeränranta.setNeighbors(Vector("niemenkarki" -> niemenkarki, "rantasauna" -> rantasauna, "smt" -> smt ))
  smt.setNeighbors(Vector("jämeränranta" -> jämeränranta, "kappelipuisto" -> kappelipuisto, "yths" -> yths))
  yths.setNeighbors(Vector("niemenkarki" -> niemenkarki, "smt" -> smt, "maarintalo" -> maarintalo))
  kappelipuisto.setNeighbors(Vector("smt" -> smt,  "ossinlampi" -> ossinlampi, "rantasauna" -> rantasauna))
  rantasauna.setNeighbors(Vector("jämeränranta" -> jämeränranta, "kappelipuisto" -> kappelipuisto, "otaranta" -> otaranta, "jmt1" -> jmt1))
  jmt1.setNeighbors(Vector("rantasauna" -> rantasauna, "täffä" -> täffä, "ossinlampi" -> ossinlampi))
  otaranta.setNeighbors(Vector("rantasauna" -> rantasauna, "täffä" -> täffä))
  täffä.setNeighbors(Vector("jmt1" -> jmt1, "otaranta" -> otaranta, "alvarinaukio" -> alvarinaukio))
  alvarinaukio.setNeighbors(Vector("kandikeskus" -> kandikeskus, "täffä" -> täffä, "kauppakorkeakoulu" -> kauppakorkeakoulu))
  kandikeskus.setNeighbors(Vector("ossinlampi" -> ossinlampi, "alvarinaukio" -> alvarinaukio))
  kauppakorkeakoulu.setNeighbors(Vector("maarintalo" -> maarintalo, "alvarinaukio" -> alvarinaukio))
  maarintalo.setNeighbors(Vector("yths" -> yths, "ossinlampi" -> ossinlampi, "kauppakorkeakoulu" -> kauppakorkeakoulu))
  ossinlampi.setNeighbors(Vector("kappelipuisto" -> kappelipuisto, "jmt1" -> jmt1, "kandikeskus" -> kandikeskus, "maarintalo" -> maarintalo))
  fly.setNeighbors(Vector("alvarinaukio" -> alvarinaukio, "kandikeskus" -> kandikeskus, "täffä" -> täffä, "kauppakorkeakoulu" -> kauppakorkeakoulu,"otaranta" -> otaranta, "jmt1" -> jmt1,"ossinlampi" -> ossinlampi,"maarintalo" -> maarintalo, "yths" -> yths, "jämeränranta" -> jämeränranta, "niemenkarki" -> niemenkarki, "rantasauna" -> rantasauna, "smt" -> smt, "kappelipuisto" -> kappelipuisto))


  // Creates characters of the game: otakettu (otaFox), otajänis (otaRabbit), teekkariposse/loud people (teekkaris), magic mushroom (mushroom)
  // Initializes vector chance, which contains objects that cause chances.
  // Creates game's world (otaniemi)
  private val rabbitCanSpawn = Buffer[Area](niemenkarki, täffä, alvarinaukio, kauppakorkeakoulu)
  private val eagleCanSpawn = Buffer[Area](niemenkarki, jämeränranta, rantasauna)

  val otaRabbit =         new Otajanis(areasBuffer.toVector, rabbitCanSpawn.toVector)
  val teekkaris =         new LoudPeople(areasBuffer.toVector)
  val mushroom =          new MagicMushroom(areasBuffer.toVector)
  val otaEagle =          new Otakotka(areasBuffer.toVector, eagleCanSpawn.toVector, fly)
  private val chance =    Vector[(LoudPeople, MagicMushroom, Otakotka)]((teekkaris, mushroom, otaEagle))
  val otaFox =            new Otakettu(kappelipuisto, chance, otaRabbit)
  val otaniemi =          new Map(otaFox, otaRabbit, areasBuffer.toVector, chance)


  // Counts the turns and sets the limit for them. Plays a turn.
  var turnCount = 0
  val timeLimit = 20


  def playTurn(command: String) = {
    val action = new Action(command, mushroom)
    val outcomeReport = action.execute(this.otaFox)
    if (outcomeReport.isDefined) {
      if(!this.isComplete && !(outcomeReport.get.split(" ").head == "Pelissä")){
        this.turnCount += 1
        if (!this.isComplete && !(outcomeReport.get.split(" ").head == "Et")) {
          otaRabbit.move()
        }
      }
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


  // Game's end condition's status, messages.
  def isComplete = this.otaFox.location == this.otaRabbit.location
  def isOver = this.isComplete || this.otaFox.hasQuit || this.turnCount == this.timeLimit


  def welcomeMessage = "Tervetuloa! \nOtaketulla on yö aikaa saada Otajänis kiinni. Pidä kiirettä!\n\n" +
                       "Pelissä voi tuntua ärsyttävältä kirjoittaa paikkojen nimiä uudelleen ja uudelleen, mutta se on todetusti " +
                       "paras tapa opettaa fukseille Otaniemen keskeiset sijainnit."

  def goodbyeMessage = {
    if (this.isComplete)
      "\n\n\n\n\n\n VOITIT PELIN!\n Otajänis löytyi paikasta " + this.otaRabbit.location.name + "."
    else if (this.turnCount == this.timeLimit) {
      "\n\n\n\n\n\nVoi ei! Aika loppui, Otakettu kuoli nälkään :("
    } else
      "\n\n\n\n\n\nLuovuttaja! Yrittäisit edes."
  }


  private var counter = 0
  def info = {

    var rabbitLocation = ""
    if (mushroom.isFound && this.counter <= 2) {
      rabbitLocation = otaRabbit.toString + "\n"
      this.counter += 1
    }
    // Uncomment this line to see all locations of chances and the rabbit.
    //"\n" + mushroom.toString + "\n" + rabbitLocation + teekkaris.toString + "\n" + otaEagle.toString
    " "
  }


  def commands = {
    "\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + "Komennot: \nmene *paikan nimi* - Liiku kartalla\nsyö  - Syö sieni (jos olet sen löytänyt)\napua  - Avaa Apua-valikon."
  }

}