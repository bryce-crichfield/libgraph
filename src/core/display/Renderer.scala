package core.display

import core.math.{Vector, Color, Normalized, Screen, Projection}
import scala.Conversion

// Freshly Constructed for Each Frame
abstract class Renderer(display: Display) {
  def projection: Projection[Normalized, Screen]
  def setColor(color: Vector[Color]): Unit
  // bottomleft-topright
  def fillRect(bottomleft: Vector[Normalized], topright: Vector[Normalized]): Unit
}

