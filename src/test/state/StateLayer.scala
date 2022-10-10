package test.state

import core.state.StateLayer
import core.math.{Vector, Normalized, Projection}
import core.state.Entity
import core.display.Renderable
import core.shape.Rectangle

class TestStateLayer extends StateLayer {
    entities addOne(TestRectangle())
    // entities addOne (CircleInteractor())
    // entities addOne (Test.g)
}

given Projection[Normalized, Normalized] with
    def apply(vector: Vector[Normalized]): Vector[Normalized] = vector

class TestRectangle extends Entity {
    override def update(): Unit = ()
    override def render(): Set[Renderable] =
        Set(Rectangle[Normalized](
            Vector(0, 0, 0, 1),
            Vector(.1, .1, 0, 1),
        )) 
}