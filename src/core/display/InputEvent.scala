package core.display

import core.math.Vector

enum InputEvent {
  case Click(val position: Vector) extends InputEvent
  case DoubleClick(val position: Vector)
  case Press(val key: Char) extends InputEvent
  case Move(val last: Vector, val next: Vector) extends InputEvent
}

