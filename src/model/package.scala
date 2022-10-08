import graphics.{Renderable, Renderer}
import math.{Vector, Matrix}

package object model {
  trait Shape extends Renderable {
    var position = Vector()
    var transform = Matrix.id
    var color = Vector()
  }

  class Circle extends Shape {
    var radius: Double = 0
    override def render(renderer: Renderer): Unit = {
      renderer.setColor(color)
      renderer.fillCircle(position.x, position.y, radius)
    }
  }

  class Box extends Shape {
    var corner_two: Option[Vector] = None
    override def render(renderer: Renderer): Unit = {
      corner_two match
        case None => ()
        case Some(corner) =>
          renderer.setColor(color)
          renderer.drawLine(
            position.x,
            position.y,
            position.x,
            corner.y
          )
          renderer.drawLine(
            position.x,
            corner.y,
            corner.x,
            corner.y
          )
          renderer.drawLine(
            corner.x,
            corner.y,
            corner.x,
            position.y
          )
          renderer.drawLine(
            corner.x,
            position.y,
            position.x,
            position.y
          )
    }

    def inBounds(vector: Vector): Boolean = {
      corner_two match
        case None => false
        case Some(corner) =>
          val lower = Vector(
            Math.min(position.x, corner.x),
            Math.min(position.y, corner.y)
          )
          val upper = Vector(
            Math.max(position.x, color.x),
            Math.max(position.y, corner.y)
          )
          if vector.x > lower.x && vector.x < upper.x &&
            vector.y > lower.y && vector.y < upper.y
          then true
          else false
    }

  }

}
