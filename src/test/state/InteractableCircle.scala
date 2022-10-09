package test.state

import core.display.InputEvent
import core.display.InputEvent
import core.display.Renderable
import core.display.InputEvent
import core.display.InputEvent
import core.display.InputEvent


class InteractableCircle extends core.state.Entity {
    var circle = core.shape.Circle()
    var delta_position = core.math.Vector(0, 0, 0)
    var interaction_mode = InteractionMode.Unselected
    def respond(input: List[InputEvent]): Unit = {
        input.foreach { i => (i, interaction_mode) match
            case InputEvent.Click(position) -> InteractionMode.Selected => 
                interaction_mode = InteractionMode.Unselected
            case InputEvent.Click(position) -> InteractionMode.Unselected 
                if (position.relative_distance(circle.position) <= circle.radius) => 
                interaction_mode = InteractionMode.Selected
            case InputEvent.Move(last, next) -> InteractionMode.Selected => 
                circle.position = next
            case _ => ()
        }
    }

    def update(): Unit = {
        interaction_mode match
            case InteractionMode.Selected => 
                circle.color = core.math.Vector(0, 1, 0)
            case InteractionMode.Unselected =>
                circle.color = core.math.Vector(1, 0, 0)
    }

    override def render(): Set[Renderable] = Set(circle)

}

enum InteractionMode {
    case Selected
    case Unselected
}

import scala.collection.mutable.{ListBuffer as Buffer}
class CircleInteractor() extends core.state.Entity {
    var circles = Buffer[InteractableCircle]()
    var interactor_mode = InteractorMode.Using

    def respond(input: List[InputEvent]): Unit = {
       input.foreach { i => (i, interactor_mode) match
        case InputEvent.Press('u') -> _ => 
            interactor_mode = InteractorMode.Using
        case InputEvent.Press('b') -> _ => 
            interactor_mode = InteractorMode.Building
        case InputEvent.Click(pos) -> InteractorMode.Building =>
            val circle = new core.shape.Circle()
            circle.position = pos
            circle.radius = 0.2
            circle.color = core.math.Vector(1, 0, 0)
            val icircle = InteractableCircle()
            icircle.circle = circle
            circles.addOne(icircle)
        case _ => ()
       }
       interactor_mode match
        case InteractorMode.Building => () 
        case InteractorMode.Using => circles.foreach(_.respond(input))  
    }

    def update(): Unit = {
       interactor_mode match
        case InteractorMode.Using => circles.foreach(_.update()) 
        case _ => ()
       
    }

    override def render(): Set[Renderable] = 
        circles.flatMap(_.render()).toSet


}

enum InteractorMode {
    case Building
    case Using
}