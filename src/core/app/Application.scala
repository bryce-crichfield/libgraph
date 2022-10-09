package core.app

import core.state.StateLayer
import core.display.Display

trait Application extends App {
  val state: StateLayer
  val display: Display
  val clock: Clock

  def run(): Unit = {
    display.start()
    while (display.running()) {
      val events = display.input().poll()
      val renderables = state.update(events)
      if (clock.tick())
        display.render(renderables)
    }
    display.dispose()
  }
}
