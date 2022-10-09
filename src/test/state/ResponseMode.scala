package test.state

import core.display.InputEvent
import core.state.*

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