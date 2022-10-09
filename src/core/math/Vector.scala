package core.math

case class Vector(
    x: Double = 0,
    y: Double = 0,
    z: Double = 1,
    w: Double = 1
) {
  def x_=(value: Double) = this.copy(x = value)
  def y_=(value: Double) = this.copy(y = value)
  def z_=(value: Double) = this.copy(z = value)
  def w_=(value: Double) = this.copy(w = value)

  def r = x
  def g = y
  def b = z
  def a = w
  def r_=(value: Double) = this.copy(x = value)
  def g_=(value: Double) = this.copy(y = value)
  def b_=(value: Double) = this.copy(z = value)
  def a_=(value: Double) = this.copy(w = value)

  infix def +(that: Vector): Vector =
    Vector(
      x + that.x,
      y + that.y,
      z + that.z,
      w + that.w
    )

  infix def -(that: Vector): Vector =
    Vector(
      x - that.x,
      y - that.y,
      z - that.z,
      w - that.w
    )

  infix def *(that: Vector): Vector =
    Vector(
      x * that.x,
      y * that.y,
      z * that.z,
      w * that.w
    )

  infix def /(that: Vector): Vector =
    Vector(
      x / that.x,
      y / that.y,
      z / that.z,
      w / that.w
    )

  def sum: Double =
    x + y + z + w

  def length: Double = {
    Math.sqrt(
        (x * x) +
        (y * y) +
        (z * z) +
        (w * w)
    )
  }

  def normal: Vector = this / Vector(this.length)

  def max: Double =
    Math.max(x, Math.max(y, Math.max(z, w)))

  def min: Double =
    Math.min(x, Math.min(y, Math.min(z, w)))

  def clamp(lower: Vector, upper: Vector): Vector = {
    var out = this
    if x < lower.x then out = out.x = lower.x
    if x > upper.x then out = out.x = upper.x
    if y < lower.y then out = out.y = lower.y
    if y > upper.y then out = out.y = upper.y
    if z < lower.z then out = out.z = lower.z
    if z > upper.z then out = out.z = upper.z
    if w < lower.w then out = out.w = lower.w
    if w > upper.w then out = out.w = upper.w
    out
  }

  // Calculates Distance without the 4th component 
  def relative_distance(that: Vector): Double = 
    ((this.w = 0) - (that.w = 0)).length

  def absolute_distance(that: Vector): Double = 
    (this - that).length
  override def toString(): String =
    f"($x, $y, $z, $w)"
}
object Vector {
  def apply(id: Double): Vector =
    Vector(id, id, id, id)
}
