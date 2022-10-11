package test.state

import core.state.{StateLayer, Container}
import core.math.{Vector, Normalized, Projection, Matrix, Color}
import core.state.Entity
import core.display.Renderable
import core.shape.Rectangle
import java.text.Normalizer

class TestStateLayer extends StateLayer {
    val root: Container = TestRectangle("root")
    root.translate = Vector(-.75, -.75, 0, 1)
    val other: TestRectangle = TestRectangle("other")
    other.translate = Vector(.5, .5, 0, 1)
    other.scale = Vector(.5, .5, 0, 1)
    other.color = Vector[Color](0, 0, 1, 1)
    root.addOne(other.asInstanceOf[Container])
    entities addOne(root)
}

given Projection[Normalized, Normalized] with
    def apply(vector: Vector[Normalized]): Vector[Normalized] = vector

class TestRectangle(id: String, 
    var translate: Vector[Normalized] = Vector(0, 0, 0, 1),
    var scale: Vector[Normalized] = Vector(1, 1, 1, 1)
) extends Container {
    var color = Vector[Color](1, 0, 1,1)

    override def render(): Set[Renderable] =
        val lower = projection * Vector[Normalized](0, 0, 0, 1)
        val upper = projection * Vector[Normalized](1, 1, 1, 1)
        val size = upper - lower
        // println(f"$id {")
        // println(f"\tprojection = $projection")
        // println(f"\tlower = $lower")
        // println(f"\tupper = $upper")
        // println(f"\tsize   = $size")
        // println("}\n")

        val rectangle = Rectangle[Normalized](lower, size)
            .copy(color = this.color)
        Set(rectangle) ++ children.flatMap(_.render())
}