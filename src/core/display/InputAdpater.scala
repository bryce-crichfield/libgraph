package core.display

import core.math.{Projection, Screen, Normalized}

trait InputAdapter {
  def projection: Projection[Screen, Normalized]
  def poll(): List[InputEvent]
}