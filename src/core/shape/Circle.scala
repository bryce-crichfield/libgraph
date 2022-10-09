package core.shape

import core.display.Renderer

class Circle extends Shape {
    var radius: Double = 0
    override def render(renderer: Renderer): Unit = {
      renderer.setColor(color)
      renderer.fillCircle(position.x, position.y, radius)
    }
}