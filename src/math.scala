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

  override def toString(): String =
    f"($x, $y, $z, $w)"
}
object Vector {
  def apply(id: Double): Vector =
    Vector(id, id, id, id)
}

case class Matrix(
    a: Vector = Vector(),
    b: Vector = Vector(),
    c: Vector = Vector(),
    d: Vector = Vector()
) {

  def translate(vector: Vector): Matrix =
    Matrix(
      a.w = vector.x,
      b.w = vector.y,
      c.w = vector.z,
      d.w = vector.w
    )

  infix def *(v: Vector): Vector = {
    Vector(
      (a * v).sum,
      (b * v).sum,
      (c * v).sum,
      (d * v).sum
    )
  }

  infix def *(m: Matrix): Matrix = {
    val r = Matrix(
      Vector(a.w, b.w, c.w, d.w),
      Vector(a.z, b.z, c.z, d.z),
      Vector(a.y, b.y, c.y, d.y),
      Vector(a.x, b.x, c.x, d.x)
    )
    val e = Vector(
      (a * r.d).sum,
      (b * r.d).sum,
      (c * r.d).sum,
      (d * r.d).sum
    )
    val f = Vector(
      (a * r.c).sum,
      (b * r.c).sum,
      (c * r.c).sum,
      (d * r.c).sum
    )
    val g = Vector(
      (a * r.b).sum,
      (b * r.b).sum,
      (c * r.b).sum,
      (d * r.b).sum
    )
    val h = Vector(
      (a * r.a).sum,
      (b * r.a).sum,
      (c * r.a).sum,
      (d * r.a).sum
    )
    Matrix(e, f, g, h)
  }
  override def toString(): String =
    f"Matrix (\n  $a\n  $b\n  $c\n  $d\n)"
}
object Matrix {
  val id: Matrix = id(1)

  def id(value: Double): Matrix = {
    Matrix(
      Vector(value, 0, 0, 0),
      Vector(0, value, 0, 0),
      Vector(0, 0, value, 0),
      Vector(0, 0, 0, value)
    )
  }
}
