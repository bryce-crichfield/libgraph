package core.display

import core.math.{Vector, Normalized}

enum InputEvent {
  case Click(val position: Vector[Normalized]) extends InputEvent
  case DoubleClick(val position: Vector[Normalized])
  case Press(val key: Char) extends InputEvent
  case Move(
    val last: Vector[Normalized], 
    val next: Vector[Normalized])
     extends InputEvent
}

