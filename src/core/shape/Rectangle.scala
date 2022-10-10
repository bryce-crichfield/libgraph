package core.shape

import core.math.Vector
import core.display.Renderer

class Rectangle extends Shape {
    var width:  Double = 0
    var height: Double = 0

    def render(renderer: Renderer): Unit = 
        renderer.setColor(color)
        renderer.fillRect(position.x, position.y, width, height)
}
