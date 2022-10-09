package core.display

trait InputAdapter {
  def poll(): List[InputEvent]
}