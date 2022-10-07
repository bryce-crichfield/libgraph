import java.awt.Graphics
import javax.swing.JFrame
import java.awt.Dimension

class AWTDisplay extends Display {
  private class Frame extends javax.swing.JFrame {
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    setLocationRelativeTo(null);
  }

  private class Panel(display: AWTDisplay) extends java.awt.Canvas {
    var renderables = Set.empty[Renderable]

    override def paint(graphics: Graphics): Unit =
      super.paint(graphics)
      println("Canvas Render")
      renderables.foreach { r =>
        r.render(AWTRenderer(display, graphics))
      }
    end paint
  }

  private val panel = Panel(this)
  private val frame = Frame()

  override def render(renderables: Set[Renderable]): Unit = {
    panel.renderables = renderables
    frame.repaint()
  }

  override def start(): Unit = {
    frame.add(panel)
    frame.setVisible(true)
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

  def fillCircle(circle: Circle): Unit = {
    val sR = toScreen(Vector(circle.radius))
    val rH = sR / Vector(2, 2)
    val s = toScreen(circle.position)
    graphics.fillOval(
      s.x.toInt - rH.x.toInt,
      s.y.toInt - rH.y.toInt,
      sR.x.toInt,
      sR.y.toInt
    )
  }

}
