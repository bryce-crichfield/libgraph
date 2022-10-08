import input.InputEvent
import model.{Shape, Box, Circle}
import math.Vector
import graphics.{Renderable}

import scala.collection.mutable.{HashSet as MutSet}

package object state {
  trait State {
    def update(events: List[InputEvent]): Set[Renderable]
  }

  trait Mode {
    def dispatch(state: GraphProgram, event: InputEvent): Unit
    def shapes(): Set[Shape] = Set.empty
  }

  case object NullMode extends Mode {
    def dispatch(state: GraphProgram, event: InputEvent): Unit =
      event match
        case InputEvent.Click(position) =>
          val box = Box()
          box.position = position
          box.color = Vector(0, 1, 0)
          val selection_mode = SelectorMode(box)
          state.mode = selection_mode
        case _ => ()
  }

  class SelectorMode(box: Box) extends Mode {
    override def shapes(): Set[Shape] =
      Set(box)

    def dispatch(state: GraphProgram, event: InputEvent): Unit =
      event match
        case InputEvent.Click(position) =>
          val in_range: Set[Shape] = state.nodes.filter { shape =>
            box.inBounds(shape.position)
          }.toSet
          in_range.foreach(shape => state.nodes.remove(shape))
          in_range.foreach(shape => shape.color = Vector(0, 1, 0))
          state.mode = SelectedMode(in_range)
        case InputEvent.Move(position) =>
          box.corner_two = Some(position)
        case _ => ()
  }

  class SelectedMode(shapes_set: Set[Shape]) extends Mode {

    override def dispatch(state: GraphProgram, event: InputEvent): Unit =
      event match
        case InputEvent.Click(position) => ()
        case InputEvent.Press(key)      => ()
        case InputEvent.Move(position)  => ()

    override def shapes(): Set[Shape] =
      shapes_set
  }

  class GraphProgram extends State {
    var mode: Mode = NullMode
    var nodes: MutSet[Shape] = MutSet.fill(10) {
      val shape = new Circle()
      shape.position = Vector(
        scala.util.Random.between(-1, 1),
        scala.util.Random.between(-1, 1)
      )
      shape.radius = 0.1
      shape.color = Vector(1, 0, 0)
      shape
    }
    def update(events: List[InputEvent]): Set[Renderable] = {
      events.foreach(e => mode.dispatch(this, e))
      return nodes.toSet ++ mode.shapes()
    }

  }

}
