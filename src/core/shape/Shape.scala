package core.shape

import core.display.Renderable
import core.math.{Vector, Matrix}

trait Shape extends Renderable {
    var position = Vector()
    var transform = Matrix.id
    var color = Vector()
}
