package core.math

class SizedVector[S: SizedVector.VectorSize] private (
    val size: Int,
    init: Double = 0
) {
  protected val data: Array[Double] = Array.fill(size)(init)
  final def get[B >: S](using b: SizedVector.VectorSize[B]): Double =
    data(b() - 1)
  final def set[B >: S](value: Double)(using
      b: SizedVector.VectorSize[B]
  ): Unit =
    data.update(b() - 1, value)

  override def clone: SizedVector[S] = {
    val out = new SizedVector[S](size)
    this.data.copyToArray(out.data, 0, size - 1)
    out
  }

  protected infix def <<(
      that: SizedVector[S]
  ): Unit = {
    for (i <- 0 until data.size)
      data.update(i, that.data(i))
  }

  infix def +=(
      that: SizedVector[S]
  ): Unit = {
    for (i <- 0 until data.size)
      data.update(i, data(i) + that.data(i))
  }

  infix def -=(
      that: SizedVector[S]
  ): Unit = {
    for (i <- 0 until data.size)
      data.update(i, data(i) - that.data(i))
  }

  infix def *=(
      that: SizedVector[S]
  ): Unit = {
    for (i <- 0 until data.size)
      data.update(i, data(i) * that.data(i))
  }

  infix def /=(
      that: SizedVector[S]
  ): Unit = {
    for (i <- 0 until data.size)
      data.update(i, data(i) / that.data(i))
  }

  infix def +(
      that: SizedVector[S]
  ): SizedVector[S] = {
    val out = new SizedVector(size)
    out += this
    return out
  }

  infix def -(
      that: SizedVector[S]
  ): SizedVector[S] = {
    val out = new SizedVector(size)
    out -= this
    return out
  }

  infix def *(
      that: SizedVector[S]
  ): SizedVector[S] = {
    val out = new SizedVector(size, 1.0d)
    out *= this
    out *= that
    return out
  }

  infix def /(
      that: SizedVector[S]
  ): SizedVector[S] = {
    val out = new SizedVector(size)
    out << this
    out /= that
    return out
  }

  override def toString(): String = {
    data.map(_.toString()).toList.toString()
  }

}

object SizedVector {

  def apply(v1: Double)(using size: VectorSize[_1]): SizedVector[_1] = {
    val out = new SizedVector(size())
    out.set[_1](v1)
    out
  }

  def apply(v1: Double, v2: Double)(using
      size: VectorSize[_2]
  ): SizedVector[_2] = {
    val out = new SizedVector(size())
    out.set[_1](v1)
    out.set[_2](v2)
    out
  }

  def apply(v1: Double, v2: Double, v3: Double)(using
      size: VectorSize[_3]
  ): SizedVector[_3] = {
    val out = new SizedVector(size())
    out.set[_1](v1)
    out.set[_2](v2)
    out.set[_3](v3)
    out
  }
  def apply(v1: Double, v2: Double, v3: Double, v4: Double)(using
      size: VectorSize[_4]
  ): SizedVector[_4] = {
    val out = new SizedVector(size())
    out.set[_1](v1)
    out.set[_2](v2)
    out.set[_3](v3)
    out.set[_4](v4)
    out
  }

  type _1
  type _2 <: _1
  type _3 <: _2
  type _4 <: _3
  trait VectorSize[S]:
    def apply(): Int
  given VectorSize[_1] with
    override def apply(): Int = 1
  given VectorSize[_2] with
    override def apply(): Int = 2
  given VectorSize[_3] with
    override def apply(): Int = 3
  given VectorSize[_4] with
    override def apply(): Int = 4

  type X = _1
  type Y = _2
  type Z = _3
  type W = _4

  type R = _1
  type G = _2
  type B = _3
  type A = _4

  extension (vector: SizedVector[_1]) 
    @scala.annotation.targetName("Grow2")
    def grow(value: Double)(using size: VectorSize[_2]): SizedVector[_2] = {
      SizedVector(vector.get[_1], value)
    }
  extension (vector: SizedVector[_2]) 
    @scala.annotation.targetName("Grow3")
    def grow(value: Double)(using size: VectorSize[_3]): SizedVector[_3] = {
      SizedVector(vector.get[_1], vector.get[_2], value)
    }
    @scala.annotation.targetName("Shrink1")
    def shrink(using size: VectorSize[_1]): SizedVector[_1] = {
      SizedVector(vector.get[_1])
    }
  extension (vector: SizedVector[_3]) 
    @scala.annotation.targetName("Grow4")
    def grow(value: Double)(using size: VectorSize[_4]): SizedVector[_4] = {
      SizedVector(vector.get[_1], vector.get[_2], vector.get[_3], value)
    }
    @scala.annotation.targetName("Shrink2")
    def shrink(using size: VectorSize[_2]): SizedVector[_2] = {
      SizedVector(vector.get[_1], vector.get[_2])
    }
  extension (vector: SizedVector[_4]) 
    @scala.annotation.targetName("Shrink3")
    def shrink(using size: VectorSize[_3]): SizedVector[_3] = {
      SizedVector(vector.get[_1], vector.get[_2], vector.get[_3])
    }

}
