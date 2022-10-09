package core.state

import core.display.{Renderable, InputEvent}


class InputResponder(var response_mode: ResponseMode) {
    def respond(input: List[InputEvent]): List[ModelEvent[_]] =
        response_mode.update(input, this)
}

trait ResponseMode {
    def update(input: List[InputEvent], multiplexer: InputResponder): List[ModelEvent[_]]
}






