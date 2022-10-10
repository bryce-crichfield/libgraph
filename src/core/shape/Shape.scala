package core.shape

import core.display.{Renderer, Renderable}
import core.math.{Vector, Matrix, Normalized, Color, Projection}

// all shape vectors need to arrive in the renderable as normalized coordinates
// we should ensure that each shape is built from only subregions of normalized
trait Shape[S <: Normalized] extends Renderable {
    val position: Vector[S]
    val size: Vector[S]
    val color: Vector[Color]
}


case class Rectangle[S <: Normalized] private (
    position: Vector[S],
    size: Vector[S],
    color: Vector[Color],
    projection: Projection[S, Normalized]
) extends Shape[S] {
    override def render(renderer: Renderer): Unit = {
        val corner = position + (size.w = 0)
        val normal_br = projection(position)
        val normal_tl = projection(corner)
        // println( normal_br)
        // println (normal_tl)
        // println("")
        renderer.setColor(color)
        renderer.fillRect(normal_br, normal_tl)
    }
}
object Rectangle {
    def apply[S <: Normalized](position: Vector[S], size: Vector[S])
        (using projection: Projection[S, Normalized]): Rectangle[S] = {
        Rectangle[S](position, size, Vector[Color](1, 0, 1, 1), projection)
    }
}
