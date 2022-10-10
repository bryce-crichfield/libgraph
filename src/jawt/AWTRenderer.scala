package jawt

import core.display.{Display, Renderer}
import core.math.{Vector, Color, Normalized, Screen, Projection, Matrix, Default}

import java.awt.Graphics
import java.text.Normalizer

class AWTRenderer(display: Display, graphics: Graphics)
    extends Renderer(display) {
  def projection = new Projection[Normalized, Screen] {
    def apply(vector: Vector[Normalized]): Vector[Screen] = 
      // TODO: Confirm this doesnt' require y-axis mirroring
      val delta = (vector  + 1) / 2
      val out = (delta.idmap[Screen] * display.getSize())
      out
  }
  def setColor(vector: Vector[Color]): Unit = {
    graphics.setColor(
      new java.awt.Color(
        vector.r.toFloat,
        vector.g.toFloat,
        vector.b.toFloat,
        vector.a.toFloat
      )
    )
  }

  def fillRect(bottomleft: Vector[Normalized], topright: Vector[Normalized]): Unit = {
    val screen_a: Vector[Screen] = projection(bottomleft)
    val screen_b: Vector[Screen] = projection(topright)
    val size = screen_b - screen_a
    graphics.fillRect(
      screen_a.x.toInt, 
      screen_a.y.toInt,
      size.x.toInt, 
      size.y.toInt
    )
  }
}
