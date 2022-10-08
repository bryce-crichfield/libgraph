package shape

import graphics.Renderable
import math.{Vector, Matrix}

trait Shape extends Renderable {
    var position = Vector()
    var transform = Matrix.id
    var color = Vector()
}
