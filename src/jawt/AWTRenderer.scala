package jawt

import graphics.{Display, Renderer}
import math.Vector

import java.awt.Graphics

class AWTRenderer(display: Display, graphics: Graphics)
    extends Renderer(display) {
  def setColor(vector: Vector): Unit = {
    graphics.setColor(
      new java.awt.Color(
        vector.r.toFloat,
        vector.g.toFloat,
        vector.b.toFloat,
        vector.a.toFloat
      )
    )
  }

  def drawLine(x1: Double, y1: Double, x2: Double, y2: Double): Unit = {
    val s1 = toScreen(Vector(x1, y1))
    val s2 = toScreen(Vector(x2, y2))
    graphics.drawLine(
      s1.x.toInt,
      s1.y.toInt,
      s2.x.toInt,
      s2.y.toInt
    )
  }

  def fillCircle(x: Double, y: Double, r: Double): Unit = {
    val sR = display.getSize().max * r
    val rH = sR / 2
    val s = toScreen(Vector(x, y))
    graphics.fillOval(
      s.x.toInt - rH.toInt,
      s.y.toInt - rH.toInt,
      sR.toInt,
      sR.toInt
    )
  }
}
