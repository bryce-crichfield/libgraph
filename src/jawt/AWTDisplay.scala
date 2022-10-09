package jawt

import core.display.{Display, Renderable, Renderer, InputEvent, InputAdapter}
import core.math.{Vector, Matrix}

import scala.collection.mutable.{PriorityQueue as ZBuffer}
import java.awt.Graphics
import javax.swing.JFrame
import java.awt.Dimension
import java.awt.event.MouseEvent
import java.awt.event.KeyEvent
import core.display.RenderableZOrder
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent


class AWTDisplay extends Display {
  private val panel = AWTDisplay.Panel(this)
  private val frame = AWTDisplay.Frame()
  private val adapter = AWTInput(this)

  override def render(renderables: ZBuffer[Renderable]): Unit = {
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

  override def dispose(): Unit = 
    frame.dispose()

  def getSize(): Vector = {
    val size = frame.getSize()
    Vector(x = size.width, y = size.height)
  }

  def setSize(w: Int, h: Int): Unit = {
    frame.setSize(w, h)
    panel.setSize(w, h)
  }

  def running(): Boolean = 
    frame.running

}

object AWTDisplay {
  private class Frame extends javax.swing.JFrame {
    var running: Boolean = true
    
    addWindowListener(new WindowAdapter {
      override def windowClosing(e: WindowEvent): Unit = 
        running = false
    })

    setLocationRelativeTo(null);
  }

  private class Panel(val display: AWTDisplay) extends javax.swing.JPanel {
    var renderables = ZBuffer.empty(RenderableZOrder)
    println(f"Rendering $renderables")
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
