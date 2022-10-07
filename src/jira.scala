class GraphicsEngine(val state: State, val display: Display, val clock: Clock) {
  def run(): Unit = {
    display.start()
    while (true) {
      var events = display.poll()
      val renderables = state.update(events)
      if (clock.tick())
        display.render(renderables)
    }
  }
}

class Clock (val fps: Int = 60) {
  val time_ns: Long = (1e9 / fps).toLong
  var lastTick: Long = 0
  def tick(): Boolean = {
    var current = System.nanoTime()
    if (current - lastTick > time_ns) then {
      lastTick = current
      true
    } else false
  }
}

trait State {
  def update(events: List[InputEvent]): Set[Renderable] = Set.empty
}

class TestState extends State {
    var shapes = scala.collection.mutable.HashSet[Shape]()
    override def update(events: List[InputEvent]): Set[Renderable] = {
        events.foreach(dispatch)
        shapes.toSet
    }

    def dispatch(event: InputEvent): Unit = {
        event match
            case InputEvent.Click(position) =>
                println("Shape Add")
                val circle = new Circle()
                circle.position = position
                circle.radius = .05
                circle.color = Vector(1, 0, 0)
                shapes.add(circle)
            case InputEvent.Press(key) => println(f"Key Event $key")
    }
}

object Test extends App {
  val engine = GraphicsEngine(
    TestState(),
    AWTDisplay(), 
    Clock(30)
  )
  engine.display.setSize(400, 400)
  engine.run()
}
