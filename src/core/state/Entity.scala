package core.state

import core.display.{InputEvent, Renderable}

import scala.collection.mutable.{ListBuffer as Buffer}

trait Entity {
    def update(): Unit
    def render(): Set[Renderable] = Set.empty
}

type Responder = PartialFunction[InputEvent, Unit]
type Subscription = () => Unit

trait InteractableEntity extends Entity {

    val responders: Buffer[Responder] = Buffer.empty
    def on(f: Responder): Subscription = {
        responders.addOne(f.orElse(_ => ()))
        () => { responders.filterInPlace(_ != f) }
    }
    def respond(input: List[InputEvent]): Unit = {
        for {
            responder <- responders
            event <- input
        } responder apply event
    }
}
