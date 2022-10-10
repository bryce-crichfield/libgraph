package jawt

import core.display.{Display, InputEvent, InputAdapter}
import core.math.{Vector, Screen, Normalized, Projection, Default, Matrix}

import java.awt.event.{MouseEvent, KeyEvent}
import core.math.Matrix

private class AWTInput(val display: AWTDisplay) extends InputAdapter {
  private val mouseAdapter = new AWTInput.MouseAdapter(this)
  private val keyAdpater = new AWTInput.KeyAdapter(this)
  private val events = new scala.collection.mutable.ListBuffer[InputEvent]()

  def projection = new Projection[Screen, Normalized] {
    def apply(vector: Vector[Screen]): Vector[Normalized] = {
      val delta = vector / display.getSize()
      ((delta * 2) - 1 ).idmap[Normalized].flip(y = true)
    }
  }

  def poll(): List[InputEvent] = {
    val out = events.toList
    events.clear()
    out.toList
  }

  def join(component: java.awt.Component): Unit = {
    component.addMouseListener(mouseAdapter)
    component.addMouseMotionListener(mouseAdapter)
    component.addKeyListener(keyAdpater)
  }
}

object AWTInput {
  private class MouseAdapter(val input: AWTInput)
      extends java.awt.event.MouseAdapter {
    var mouse_position: Vector[Normalized] = Vector(0, 0, 0)

    private def extract_position(e: MouseEvent): Vector[Screen] = {
      Vector[Screen](e.getX(), e.getY(), 0, 1)
    }

    override def mouseClicked(e: MouseEvent): Unit =
      val position: Vector[Normalized] =
        input.projection(extract_position(e))
      println(f"AWT Normalized Click at $position")
      input.events.addOne(InputEvent.Click(position))

    override def mouseMoved(e: MouseEvent): Unit =
      val position: Vector[Normalized] =
        input.projection(extract_position(e))
      input.events.addOne(InputEvent.Move(mouse_position, position))
      mouse_position = position

  }

  private class KeyAdapter(val input: AWTInput)
      extends java.awt.event.KeyAdapter {
    override def keyPressed(e: KeyEvent): Unit =
      input.events addOne InputEvent.Press(e.getKeyChar())
  }
}
