enum InputEvent {
    case Click(val position: Vector) extends InputEvent
    case Press(val key: Char) extends InputEvent
}

trait InputManager {
    def poll(): List[InputEvent]
}