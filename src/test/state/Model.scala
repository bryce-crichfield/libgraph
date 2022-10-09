package test.state 

import core.state.{Model, ModelEvent}
import core.display.Renderable

class TestModel extends Model {
    var message: Int = 0
    def update(model_event: List[ModelEvent[_]]): Set[Renderable] = 
        val out = model_event.flatMap { 
            case p: Publish =>
                List(p.update(this)) 
            case m: ModelEvent[_] => 
                m.update(this)
                List.empty[Renderable]
        }
        out.toSet
}

