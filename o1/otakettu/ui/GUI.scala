package o1.otakettu.ui


import o1.{Pic, show}

import scala.swing._
import scala.swing.event._
import javax.swing.{ImageIcon, UIManager}
import o1.otakettu.Adventure

import scala.swing.GridBagPanel.Anchor.{NorthWest, SouthWest}
import scala.swing.GridBagPanel.Fill



object GUI extends SimpleSwingApplication {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  private val newlines = "\n\n\n\n\n\n\n\n\n\n\n"

  def top = new MainFrame {

    // Access to the application’s internal logic:

    val game = new Adventure
    val player = game.otaFox
    val rabbit = game.otaRabbit


    // Components:

    val locationAndTurnOutput = new TextArea(7, 50) {
      editable = false
      wordWrap = true
      lineWrap = true
    }


    val input = new TextField(40) {
      minimumSize = preferredSize
    }
    this.listenTo(input.keys)
    val turnCounter = new Label

    // Events:

    this.reactions += {
      case keyEvent: KeyPressed =>
        if (keyEvent.source == this.input && keyEvent.key == Key.Enter /*&& !this.game.isOver*/) {
          val command = this.input.text.trim
          if (command.nonEmpty) {
            this.input.text = ""
            this.playTurn(command)
          }
        }
    }

    // Layout:


    private var gameMap = new Label("", new ImageIcon( game.otaniemi.showMap() ), Alignment.Center)


    this.contents = new GridBagPanel {

      layout += new Label("Sijainti:") -> new Constraints(0, 0, 1, 1, 0, 1, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Komento:") -> new Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += turnCounter -> new Constraints(0, 3, 2, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += input -> new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += gameMap -> new Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += locationAndTurnOutput -> new Constraints(2, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)

    }

    // Menu:

    this.menuBar = new MenuBar {
      contents += new Menu("Program") {
        val quitAction = Action("Quit") {
          dispose()
        }
        contents += new MenuItem(quitAction)
      }
    }

    // Set up the GUI’s initial state:

    this.title = game.title
    this.updateInfo(this.game.welcomeMessage + game.commands)
    this.location = new Point(50, 50)
    this.minimumSize = new Dimension(200, 200)
    this.pack()
    this.input.requestFocusInWindow()


    def playTurn(command: String) = {

      val turnReport = this.game.playTurn(command)
      if (this.player.hasQuit) {
        this.dispose()
      } else {
        this.updateInfo(turnReport + game.info + game.commands)
        this.input.enabled = !this.game.isOver
      }
    }



    def updateInfo(info: String) = {
      if (!this.game.isOver) {
        this.locationAndTurnOutput.text = this.player.location.fullDescription + newlines + info
      } else {
        this.locationAndTurnOutput.text = this.player.location.fullDescription + newlines + info + "\n\n" + this.game.goodbyeMessage
      }
      this.turnCounter.text = "Turns played: " + this.game.turnCount
      this.gameMap.icon = new ImageIcon( game.otaniemi.showMap() )
    }

  }

}

