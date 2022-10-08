package state

import input.{InputEvent}
import graphics.{Renderable}

trait StateLayer {
    val responder: InputResponder
    val model: Model

    def update(input: List[InputEvent]): Set[Renderable] = {
        val model_events = responder.respond(input)
        model.update(model_events)
    }
}

class TestStateLayer extends StateLayer {
    val responder = InputResponder(TestResponseMode())
    val model = TestModel()
}
