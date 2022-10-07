import java.awt.Graphics
import javax.swing.JFrame
import java.awt.Dimension
import java.awt.event.MouseEvent
import java.awt.event.KeyEvent

class AWTDisplay extends Display {
  private class Frame extends javax.swing.JFrame {
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    setLocationRelativeTo(null);
  }

  private class Panel(display: AWTDisplay) extends javax.swing.JPanel {
    var renderables = Set.empty[Renderable]
    override def paintComponent(graphics: Graphics): Unit =
      renderables.foreach { r =>
        r.render(AWTRenderer(display, graphics))
      }
  }

  private class AWTInput() extends java.awt.event.MouseAdapter with InputManager { 
    val MouseAdapter = new java.awt.event.MouseAdapter {
        override def mouseClicked(e: MouseEvent): Unit = 
            val coord = Vector(e.getX(), e.getY()) / getSize()
            val normal = (coord * Vector(2, 2)) - Vector(1, 1)
            events addOne InputEvent.Click(normal)
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
        component.addKeyListener(KeyAdapater)
  }

  private val panel = Panel(this)
  private val frame = Frame()
  private val input = AWTInput()

  override def render(renderables: Set[Renderable]): Unit = {
    panel.renderables = renderables
    panel.repaint() 
  }

  override def poll(): List[InputEvent] = 
    input.poll()

  override def start(): Unit = {
    frame.add(panel)
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

  def fillCircle(circle: Circle): Unit = {
    val sR = display.getSize().max * circle.radius
    val rH = sR / 2
    val s = toScreen(circle.position)
    graphics.fillOval(
      s.x.toInt - rH.toInt,
      s.y.toInt - rH.toInt,
      sR.toInt,
      sR.toInt
    )
  }

}
