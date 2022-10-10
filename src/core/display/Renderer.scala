package core.display

import core.math.Vector

// Freshly Constructed for Each Frame
abstract class Renderer(display: Display) {
  def toScreen(vector: Vector): Vector = {
    val size = display.getSize()
    val normal = (vector + Vector(1, 1)) / Vector(2, 2)
    normal * display.getSize()
  }

  def setColor(color: Vector): Unit
  def drawLine(x1: Double, y1: Double, x2: Double, y2: Double): Unit
  def fillCircle(x: Double, y: Double, r: Double): Unit

  def fillRect(x: Double, y: Double, w: Double, h: Double): Unit 
  def text(msg: String, x: Double, y: Double, size: Double): Unit
}

