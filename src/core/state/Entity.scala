package core.state

import core.display.{InputEvent, Renderable}
import core.math.Vector

import scala.collection.mutable.{ListBuffer as Buffer}
import core.math.{Normalized, Matrix}
import core.math.Projection
import core.shape.Rectangle
import java.text.Normalizer

trait Entity {
    def update(): Unit 
    def render(): Set[Renderable] 
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

trait Container extends Entity {
    var inherited: Matrix[Normalized] = Matrix.id
    var projection: Matrix[Normalized] = Matrix.id

    var translate: Vector[Normalized]
    var scale: Vector[Normalized]
    val children: Buffer[Container] = Buffer.empty

    protected def transform(): Matrix[Normalized] = {
        val scaled_base = Matrix.scale(scale) * translate
        inherited & Matrix.translate(scaled_base) & Matrix.scale(scale)
    }

    def update(): Unit = 
        projection = transform()
        children.foreach { child =>
            child.inherited = projection
            child.update()  
        }
    end update

    def addOne(child: Container): Unit = {
        children.addOne(child)
    }
}

