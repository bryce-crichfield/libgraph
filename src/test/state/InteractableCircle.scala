package test.state

import core.display.InputEvent
import core.display.InputEvent
import core.display.Renderable
import core.display.InputEvent
import core.display.InputEvent
import core.display.InputEvent
import java.awt.event.KeyEvent
import core.display.InputEvent

class SpecialCircle extends core.shape.Circle {
  var interaction_mode = SpecialCircleMode.Unselected
}
enum SpecialCircleMode {
  case Selected
  case Unselected
}

import scala.collection.mutable.{ListBuffer as Buffer}
class CircleInteractor() extends core.state.InteractableEntity {
  var circles = Buffer[SpecialCircle]()
  var interactor_mode = InteractorMode.Using

  this.on {
    case InputEvent.Press('b') =>
      interactor_mode = InteractorMode.Building
    case InputEvent.Press('u') =>
      interactor_mode = InteractorMode.Using
  }

  this.on { case InputEvent.Click(position) =>
    interactor_mode match
      case InteractorMode.Building =>
        val circle = SpecialCircle()
        circle.position = position
        circle.radius = 0.1
        circles.addOne(circle)
      case InteractorMode.Using =>
        val selected =
          circles.find(_.interaction_mode == SpecialCircleMode.Selected)
        selected match
          case None =>
            val nearest = findNearestCircle(position)
            nearest.map { circle =>
              circle.interaction_mode = SpecialCircleMode.Selected
            }
          case Some(circle) =>
            circle.interaction_mode = SpecialCircleMode.Unselected
  }

  this.on { case InputEvent.Move(last, next) =>
    circles.filter { _.interaction_mode == SpecialCircleMode.Selected }
    .foreach(_.position = next)  
  }

  private def findNearestCircle(
      position: core.math.Vector
  ): Option[SpecialCircle] = {
    circles
      .map { circle =>
        circle -> circle.position.relative_distance(position)
      }
      .filter { case (circle, distance) =>
        distance <= 0.1
      }
      .sortWith((t1, t2) => t1._2 < t1._2)
      .headOption
      .map(_._1)
  }

  def update(): Unit = {
    circles.foreach { circle =>
      circle.interaction_mode match
        case SpecialCircleMode.Selected =>
          circle.color = core.math.Vector(0, 1, 0)
        case SpecialCircleMode.Unselected =>
          circle.color = core.math.Vector(1, 0, 0)
    }
  }

  override def render(): Set[Renderable] =
    circles.map { (circle: SpecialCircle) =>
      circle.asInstanceOf[Renderable]
    }.toSet

}

enum InteractorMode {
  case Building
  case Using
}
