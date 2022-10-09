package jawt

import core.display.{Display, Renderable, Renderer, InputEvent, InputAdapter}
import core.math.{Vector, Matrix}

import java.awt.Graphics
import javax.swing.JFrame
import java.awt.Dimension
import java.awt.event.MouseEvent
import java.awt.event.KeyEvent


class AWTDisplay extends Display {
  private val panel = AWTDisplay.Panel(this)
  private val frame = AWTDisplay.Frame()
  private val adapter = AWTInput(this)

  override def render(renderables: Set[Renderable]): Unit = {
    panel.renderables = renderables
    panel.repaint()
  }

  override def input(): InputAdapter = adapter

  override def start(): Unit = {
    frame.add(panel)
    adapter.join(frame)
    adapter.join(panel)
    frame.setVisible(true)
    frame.repaint()
  }

  def getSize(): Vector = {
    val size = frame.getSize()
    Vector(x = size.width, y = size.height)
  }

  def setSize(w: Int, h: Int): Unit = {
    frame.setSize(w, h)
    panel.setSize(w, h)
  }

}

object AWTDisplay {
  private class Frame extends javax.swing.JFrame {
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    setLocationRelativeTo(null);
  }

  private class Panel(val display: AWTDisplay) extends javax.swing.JPanel {
    var renderables = Set.empty[Renderable]
    override def paintComponent(graphics: Graphics): Unit =
      graphics.setColor(java.awt.Color.DARK_GRAY)
      graphics.fillRect(
        0,
        0,
        display.getSize().x.toInt,
        display.getSize().y.toInt
      )
      renderables.foreach { r =>
        r.render(AWTRenderer(display, graphics))
      }
  }
}
