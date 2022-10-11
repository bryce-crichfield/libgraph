package core.math

import scala.collection.mutable.ListBuffer

case class Matrix[S<: CoordinateSystem](
    a: Vector[S] = Vector(),
    b: Vector[S] = Vector(),
    c: Vector[S] = Vector(),
    d: Vector[S] = Vector()
) {

  def translate(vector: Vector[S]): Matrix[S] =
    Matrix(
      a.w = vector.x,
      b.w = vector.y,
      c.w = vector.z,
      d.w = vector.w
    )

  infix def *(v: Vector[S]): Vector[S] = {
    Vector(
      (a * v).sum,
      (b * v).sum,
      (c * v).sum,
      (d * v).sum
    )
  }

  infix def andThen (m: Matrix[S]): Matrix[S] = {
    val a = this.toList
    val b = m.toList
    var c = ListBuffer.fill(4)(ListBuffer.fill(4)(0.0))
    for (i <- 0 until 4) {
      for (j <- 0 until 4) {
        var sum = 0.0
        for (k <- 0 until 4) {
          sum += a(i)(k) * b(k)(j)
        }
        c(i).update(j, sum)
      }
    }
    return Matrix.from(c.flatten.toList)

    // // val r: Matrix[S] = Matrix(
    // //   Vector(a.w, b.w, c.w, d.w),
    // //   Vector(a.z, b.z, c.z, d.z),
    // //   Vector(a.y, b.y, c.y, d.y),
    // //   Vector(a.x, b.x, c.x, d.x)
    // // )
    // val r: Matrix[S]= Matrix(
    //   m.d, m.c, m.b, m.a
    // )
    // val e: Vector[S] = Vector(
    //   (a * r.d).sum,
    //   (b * r.d).sum,
    //   (c * r.d).sum,
    //   (d * r.d).sum
    // )
    // val f: Vector[S] = Vector(
    //   (a * r.c).sum,
    //   (b * r.c).sum,
    //   (c * r.c).sum,
    //   (d * r.c).sum
    // )
    // val g: Vector[S] = Vector(
    //   (a * r.b).sum,
    //   (b * r.b).sum,
    //   (c * r.b).sum,
    //   (d * r.b).sum
    // )
    // val h: Vector[S] = Vector(
    //   (a * r.a).sum,
    //   (b * r.a).sum,
    //   (c * r.a).sum,
    //   (d * r.a).sum
    // )
    // Matrix[S](e, f, g, h)
  }

  def toList: List[List[Double]] = 
    List(a.toList, b.toList, c.toList, d.toList)
  override def toString(): String =
    f"Matrix (\n  $a\n  $b\n  $c\n  $d\n)"
}
object Matrix {
  def id[S<: CoordinateSystem]: Matrix[S] = id(1)
  def id[S<: CoordinateSystem](vector: Vector[S]): Matrix[S] = {
    Matrix.from[S](
      vector.x, 0, 0, 0,
      0, vector.y, 0, 0,
      0, 0, vector.z, 0,
      0, 0, 0, vector.w
    )
  }

  def id[S<: CoordinateSystem](value: Double): Matrix[S] = {
    Matrix.id(Vector.id(value))
  }

  def from[S <: CoordinateSystem](values: Double*): Matrix[S] = {
    val list = values.toList
    assert(list.size == 16)
    Matrix[S](
      Vector[S](list(0), list(1), list(2), list(3)),
      Vector[S](list(4), list(5), list(6), list(7)),
      Vector[S](list(8), list(9), list(10), list(11)),
      Vector[S](list(12), list(13), list(14), list(15))
    )
  }

  def from[S <: CoordinateSystem](list: List[Double]): Matrix[S] = {
    assert(list.size == 16)
    Matrix[S](
      Vector[S](list(0), list(1), list(2), list(3)),
      Vector[S](list(4), list(5), list(6), list(7)),
      Vector[S](list(8), list(9), list(10), list(11)),
      Vector[S](list(12), list(13), list(14), list(15))
    )
  }


  def mirror[S <: CoordinateSystem](vector: Vector[S]): Matrix[S] = {
    Matrix.id[S](vector)
  }

  def translate[S <: CoordinateSystem](vector: Vector[S]): Matrix[S] = {
    Matrix.from[S] (
      1, 0, 0, vector.x,
      0, 1, 0, vector.y,
      0, 0, 1, vector.z,
      0, 0, 0, 1
    )
  }

  def scale[S <: CoordinateSystem](vector: Vector[S]): Matrix[S] = {
    Matrix.id[S](vector)
  }
}
