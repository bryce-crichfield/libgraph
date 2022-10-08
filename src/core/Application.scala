package core

import state.State
import graphics.Display

class Application(
    val state: State,
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
