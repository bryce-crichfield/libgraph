import state.State
import graphics.Display

class GraphicsEngine(
  val state: State, 
  val display: Display, 
  val clock: Clock
) {
  def run(): Unit = {
    display.start()
    while (true) {
      var events = display.poll()
      // val renderables = state.update(events)
      if (clock.tick())
        display.render(Set())
        // display.render(renderables)
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

object Test extends App {
  val engine = GraphicsEngine(
    state.GraphProgram(),
    jawt.AWTDisplay(), 
    Clock(30)
  )
  engine.display.setSize(400, 400)
  engine.run()
}
