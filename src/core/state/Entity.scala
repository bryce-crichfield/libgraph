package core.state

import core.display.{InputEvent, Renderable}

trait Entity {
    def respond(input: List[InputEvent]): Unit
    def update(): Unit
    def render(): Set[Renderable] = Set.empty
}
