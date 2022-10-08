package state

import input.{InputEvent}
import graphics.{Renderable}
import input.InputEvent
import input.InputEvent

class InputResponder(var response_mode: ResponseMode) {
    def respond(input: List[InputEvent]): List[ModelEvent[_]] =
        response_mode.update(input, this)
}

trait ResponseMode {
    def update(input: List[InputEvent], multiplexer: InputResponder): List[ModelEvent[_]]
}

class TestResponseMode extends ResponseMode {
    def update(input: List[InputEvent], multiplexer: InputResponder): List[ModelEvent[_]] = {
        input.flatMap { 
            case InputEvent.Click(position) => 
                println("GOT UR CLICK")
                List(Publish())
            case _ => List()
        }
    }

}




