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

  infix def & (m: Matrix[S]): Matrix[S] = {
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
    Matrix.from(c.flatten.toList)
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
