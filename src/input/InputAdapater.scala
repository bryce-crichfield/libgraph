package input

trait InputAdapter {
  def poll(): List[InputEvent]
}