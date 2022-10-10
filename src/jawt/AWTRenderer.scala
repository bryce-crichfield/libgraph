package jawt

import core.display.{Display, Renderer}
import core.math.Vector

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

  def fillRect(x: Double, y: Double, w: Double, h: Double): Unit = {
    val sP = toScreen(Vector(x, y))
    val sS = display.getSize() * Vector(w, h)
    graphics.fillRect(
      sP.x.toInt, sP.y.toInt, sS.x.toInt, sS.y.toInt
    )
  }

  def text(msg: String, x: Double, y: Double, size: Double): Unit = {
    // graphics.drawString(msg, x.toInt, y.toInt)
  }

}
