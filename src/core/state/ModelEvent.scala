package core.state

import core.display.Renderable
import core.shape.Circle

trait ModelEvent[O] {
  def update(model: Model): O
}

trait UnitModelEvent extends ModelEvent[Unit] {
    def update(model: Model): Unit 
}

