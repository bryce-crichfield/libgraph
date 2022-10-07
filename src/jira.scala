class GraphicsEngine(val state: State = ???, val display: Display = ???) {
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

class TestState extends State {
  override def update(): Set[Renderable] = {
    val circle = Circle()
    circle.position = Vector()
    circle.radius = 0.15
    circle.color = Vector(1, 0, 0)
    Set(circle)
  }
}

trait Display {
  def render(renderables: Set[Renderable]): Unit
  def start(): Unit
  def setSize(w: Int, h: Int): Unit
  def getSize(): Vector
}

object Test extends App {
  val engine = GraphicsEngine(
    TestState(),
    AWTDisplay()
  )
  engine.display.setSize(400, 400)
  engine.run(())
}