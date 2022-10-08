package jawt

import java.awt.Graphics
import javax.swing.JFrame
import java.awt.Dimension
import java.awt.event.MouseEvent
import java.awt.event.KeyEvent

import graphics.{Display, Renderable, Renderer}
import math.{Vector, Matrix}
import input.{InputEvent, InputManager}

class AWTDisplay extends Display {
  private class Frame extends javax.swing.JFrame {
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    setLocationRelativeTo(null);
  }

  private class Panel(display: AWTDisplay) extends javax.swing.JPanel {
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

  private val panel = Panel(this)
  private val frame = Frame()
  private val input = AWTInput(this)

  override def render(renderables: Set[Renderable]): Unit = {
    panel.renderables = renderables
    panel.repaint()
  }

  override def poll(): List[InputEvent] =
    input.poll()

  override def start(): Unit = {
    frame.add(panel)
    input.addToAWT(frame)
    input.addToAWT(panel)
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

class AWTRenderer(display: Display, graphics: Graphics)
    extends Renderer(display) {
  def setColor(vector: Vector): Unit = {
    graphics.setColor(
      new java.awt.Color(
        vector.r.toFloat,
        vector.g.toFloat,
        vector.b.toFloat,
        vector.a.toFloat
      )
    )
  }
  def drawLine(x1: Double, y1: Double, x2: Double, y2: Double): Unit = {
    val s1 = toScreen(Vector(x1, y1))
    val s2 = toScreen(Vector(x2, y2))
    graphics.drawLine(
      s1.x.toInt,
      s1.y.toInt,
      s2.x.toInt,
      s2.y.toInt
    )
  }

  def fillCircle(x: Double, y: Double, r: Double): Unit = {
    val sR = display.getSize().max * r
    val rH = sR / 2
    val s = toScreen(Vector(x, y))
    graphics.fillOval(
      s.x.toInt - rH.toInt,
      s.y.toInt - rH.toInt,
      sR.toInt,
      sR.toInt
    )
  }

}

private class AWTInput(display: Display) extends java.awt.event.MouseAdapter with InputManager {
  val MouseAdapter = new java.awt.event.MouseAdapter {
    private def toScreen(e: java.awt.event.MouseEvent): Vector = {
      val coord = Vector(e.getX(), e.getY()) / display.getSize()
      (coord * Vector(2, 2)) - Vector(1, 1)
    }

    override def mouseClicked(e: MouseEvent): Unit =
      events addOne InputEvent.Click(toScreen(e))

    override def mouseMoved(e: MouseEvent): Unit =
      events addOne InputEvent.Move(toScreen(e))

  }

  val KeyAdapater = new java.awt.event.KeyAdapter {
    override def keyPressed(e: KeyEvent): Unit =
      events addOne InputEvent.Press(e.getKeyChar())
  }

  var events = new scala.collection.mutable.ListBuffer[InputEvent]()
  def poll(): List[InputEvent] = {
    val out = events.toList
    events.clear()
    out.toList
  }

  def addToAWT(component: java.awt.Component): Unit =
    component.addMouseListener(MouseAdapter)
    component.addMouseMotionListener(MouseAdapter)
    component.addKeyListener(KeyAdapater)
}
