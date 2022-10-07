trait Shape extends Renderable {
  var position = Vector()
  var transform = Matrix.id
  var color = Vector()
}

class Circle extends Shape {
  var radius: Double = 0
  override def render(renderer: Renderer): Unit = {
    renderer.setColor(color)
    renderer.fillCircle(this)
  }
}
