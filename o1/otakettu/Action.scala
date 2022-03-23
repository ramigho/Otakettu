package o1.otakettu


// Executes command given by the user, if possible.
// Handles the input's.

class Action(input: String, magicMushroom: MagicMushroom) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  def execute(actor: Otakettu): Option[String] = this.verb match {
    case "mene"    => Some(actor.go(this.modifiers))
    case "apua"    => Some(this.help())
    case "syö"     => Some(magicMushroom.eat())
    case other     => None
  }


  def help() = {
    "Pelissä pelaat Otakettuna. " +
    "Tehtäväsi on saada Otajänis kiinni liikkumalla kartalla komentojen avulla. Tähän sinulla on pelin alusta 20 kierrosta aikaa. " +
    "Näet pelin aikana kartalla oman sijainnin sekä kolmen kierroksen välein myös Otajäniksen sijainnin. " +
    "Pelin aikana kartalla voi tulla vastaan sattumia, " +
    "jotka helpottavat tai vaikeuttavat pelin tavoiteen saavuttamista." +
    "\n\n\n"+
    "Sattumat: \nSIENI  - Näet jäniksen kartalla kahden vuoron ajan syötyäsi maasta löytyneen sienen. Löydettyä sienen, se on sinulla mukana kunnes päätät syödä sen." + "" +
    "\n\nMELUAVA TEEKKARILAUMA - Säikähdät ja pakenet johonkin viereiseen ruutuun\n\n" +
    "OTAKOTKA  - Otakotka tarjoutuu lennättämään sinut mihin tahansa paikkaan kartalla\n\n" +
    "Komennot: \nmene *paikan nimi* - Liiku kartalla\nsyö  - Syö sieni (jos olet sen löytänyt)\napua  - Avaa Apua-valikon."

  }


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"
}
