package core.state

import core.display.{Renderable, InputEvent}
import scala.collection.mutable.{ListBuffer as Buffer}

trait StateLayer {
    val entities: Buffer[Entity] = Buffer.empty 

    def update(input: List[InputEvent]): Set[Renderable] = {
        entities.foreach(_.respond(input))
        entities.foreach(_.update())
        entities.flatMap(_.render()).toSet
    }
}
