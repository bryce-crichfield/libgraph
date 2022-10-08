package math

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
