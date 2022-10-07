import javax.swing.Renderer
import java.awt.Graphics
import javax.swing.JFrame
import java.awt.Dimension
class GraphicsEngine (
    val state: State = ???,
    val display: Display = ??? )
{
    def run(f: => Unit): Unit = {
        display.start()
        while (true) {
            f
            val renderables = state.update()
            display.render(renderables)
        }
    }
}

trait State {
    def update(): Set[Renderable] = Set.empty
}


case class Circle(
    center: Vector3, 
    radius: Float,
    color: java.awt.Color
) extends Renderable {
    override def render(renderer: Renderer): Unit = 
        renderer.set(color)
        renderer.fill(this)
}
class TestState extends State {
    override def update(): Set[Renderable] = Set(
        Circle(Vector3(0, 0, 0), .25, new java.awt.Color(255, 0, 0))
    )
}


trait Display {
    def render(renderables: Set[Renderable]): Unit = ()
        // renderables.foreach(_.render(getRenderer()))

    def start(): Unit 
    def setSize(w: Int, h: Int): Unit
    def getSize(): (Int, Int)
    
}

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

    def getSize(): (Int, Int) = {
        val size = frame.getSize()
        (size.width, size.height)
    }

    def setSize(w: Int, h: Int): Unit = {
        frame.setSize(w, h)
        panel.setSize(w, h)
    }
    
}


trait Renderable {
    def render(renderer: Renderer): Unit = ()
}

class AWTRenderer(display: Display, graphics: Graphics) extends Renderer(display) {

    private def screenX(v: Float): Int = 
        (((v + 1) / 2) * display.getSize()._1).toInt
    private def screenY(v: Float): Int = 
        (((v + 1) / 2) * display.getSize()._2).toInt

    def set(color: java.awt.Color): Unit = {
        graphics.setColor(color)
    }
    def drawLine(x1: Float, y1: Float, x2: Float, y2: Float): Unit = {
        graphics.drawLine(
            screenX(x1), screenY(y1),
            screenX(x2), screenY(y2)
        )
    }

    def fill(circle: Circle): Unit = {
        val rx = screenX(circle.radius)
        val ry = screenY(circle.radius)
        graphics.fillOval(
            screenX(circle.center.x) - (rx / 2), screenY(circle.center.y) - (ry / 2),
            rx, ry
        )
    }

}

abstract class Renderer(display: Display) {
    def set(color: java.awt.Color): Unit
    def drawLine(x1: Float, y1: Float, x2: Float, y2: Float): Unit 
    def fill(circle: Circle): Unit
}

object Test extends App {
    val engine = GraphicsEngine(
        TestState(), 
        AWTDisplay()
    )
// 
    engine.display.setSize(400, 400)

    engine.run(())
}



case class Vector3(x: Float, y: Float, z: Float = 0) {
    inline infix def + (that: Vector3): Vector3 = {
        Vector3(x+that.x, y+that.y, z+that.z)
    }

    inline infix def - (that: Vector3): Vector3 = {
        Vector3(x-that.x, y-that.y, z-that.z)
    }

    inline infix def * (that: Vector3): Vector3 = {
        Vector3(x*that.x, y*that.y, z*that.z)
    }

    inline infix def / (that: Vector3): Vector3 = {
        Vector3(x/that.x, y/that.y, z/that.z)
    }
    
}

case class Matrix3x3(data: Array[Float] = Array.fill(9)(0f)) {
    infix def * (v3: Vector3): Vector3 = {
        Vector3 (
            x = ???,
            y = ???,
            z = ???
        )
    }
}