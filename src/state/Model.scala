package state

import graphics.Renderable

// A Model can house a lot of different data, so abstract
trait Model {
    // Side Effecting on this Model
    def update(model_events: List[ModelEvent[_]]): Set[Renderable]
}

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


