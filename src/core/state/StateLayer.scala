package core.state

import core.math.Vector
import core.display.{Renderable, InputEvent}
import scala.collection.mutable.{ListBuffer as Buffer}
import scala.collection.mutable.{PriorityQueue as ZBuffer}

trait StateLayer {
    val entities: Buffer[Entity] = Buffer.empty 

    def update(input: List[InputEvent]): ZBuffer[Renderable] = {
        entities.foreach {
            case e: InteractableEntity => e.respond(input)
            case _ => () 
        }
        entities.foreach(_.update())
        val zbuffer = ZBuffer.empty(core.display.RenderableZOrder)
        entities.foreach { entity =>
            val renderables = entity.render()
            renderables.foreach { zbuffer.addOne }    
        }
        zbuffer
    }
}


