package core

import state.StateLayer
import graphics.Display

class Application(
    val state: StateLayer,
    val display: Display,
    val clock: Clock
) {
  def run(): Unit = {
    display.start()
    while (true) {
      val events = display.input().poll()
      val renderables = state.update(events)
      if (clock.tick())
        display.render(renderables)
    }
  }
}
