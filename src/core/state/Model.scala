package core.state

import core.display.Renderable

// A Model can house a lot of different data, so abstract
trait Model {
    // Side Effecting on this Model
    def update(model_events: List[ModelEvent[_]]): Set[Renderable]
}



