package core.state

import core.display.{Renderable, InputEvent}

trait StateLayer {
    val responder: InputResponder
    val model: Model

    def update(input: List[InputEvent]): Set[Renderable] = {
        val model_events = responder.respond(input)
        model.update(model_events)
    }
}
