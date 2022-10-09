package jawt

import core.display.{Display, InputEvent, InputAdapter}
import core.math.Vector

import java.awt.event.{MouseEvent, KeyEvent}

private class AWTInput(val display: AWTDisplay) extends InputAdapter {
  private val mouseAdapter = new AWTInput.MouseAdapter(this)
  private val keyAdpater = new AWTInput.KeyAdapter(this)
  private val events = new scala.collection.mutable.ListBuffer[InputEvent]()

  def poll(): List[InputEvent] = {
    val out = events.toList
    events.clear()
    out.toList
  }

  def join(component: java.awt.Component): Unit =
    component.addMouseListener(mouseAdapter)
    component.addMouseMotionListener(mouseAdapter)
    component.addKeyListener(keyAdpater)
}

object AWTInput {
  private class MouseAdapter(val input: AWTInput)
      extends java.awt.event.MouseAdapter {
    private def toScreen(e: java.awt.event.MouseEvent): Vector = {
      val coord = Vector(e.getX(), e.getY()) / input.display.getSize()
      (coord * Vector(2, 2)) - Vector(1, 1)
    }

    override def mouseClicked(e: MouseEvent): Unit =
      input.events addOne InputEvent.Click(toScreen(e))

    override def mouseMoved(e: MouseEvent): Unit =
      input.events addOne InputEvent.Move(toScreen(e))

  }

  private class KeyAdapter(val input: AWTInput)
      extends java.awt.event.KeyAdapter {
    override def keyPressed(e: KeyEvent): Unit =
      input.events addOne InputEvent.Press(e.getKeyChar())
  }
}
