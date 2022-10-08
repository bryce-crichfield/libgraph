import math.Vector

package object input {
  enum InputEvent {
    case Click(val position: Vector) extends InputEvent
    case DoubleClick(val position: Vector)
    case Press(val key: Char) extends InputEvent
    case Move (val position: Vector) extends InputEvent
    }

    trait InputManager {
        def poll(): List[InputEvent]
    } 
}
