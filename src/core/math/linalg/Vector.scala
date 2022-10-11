package core.math.linalg

class Vector[S: core.math.linalg.Component] private (
    val size: Int,
    init: Double = 0
) {
  protected val data: Array[Double] = Array.fill(size)(init)

  final def get[B >: S](using component: Component[B]): Double =
    data(component.min_size() - 1)

  final def set[B >: S](value: Double)(using component: Component[B]): Unit =
    data.update(component.min_size() - 1, value)

  override def clone: Vector[S] = {
    val out = new Vector[S](size)
    this.data.copyToArray(out.data, 0, size - 1)
    out
  }

  protected infix def << (that: Vector[S]): Unit = {
    for (i <- 0 until data.size)
      data.update(i, that.data(i))
  }

  infix def += (that: Vector[S]): Unit = {
    for (i <- 0 until data.size)
      data.update(i, data(i) + that.data(i))
  }

  infix def -= (that: Vector[S]): Unit = {
    for (i <- 0 until data.size)
      data.update(i, data(i) - that.data(i))
  }

  infix def *= (that: Vector[S]): Unit = {
    for (i <- 0 until data.size)
      data.update(i, data(i) * that.data(i))
  }

  infix def /= (that: Vector[S]): Unit = {
    for (i <- 0 until data.size)
      data.update(i, data(i) / that.data(i))
  }

  infix def + (that: Vector[S]): Vector[S] = {
    val out = new Vector(size)
    out += this
    return out
  }

  infix def - (that: Vector[S]): Vector[S] = {
    val out = new Vector(size)
    out -= this
    return out
  }

  infix def * (that: Vector[S]): Vector[S] = {
    val out = new Vector(size, 1.0d)
    out *= this
    out *= that
    return out
  }

  infix def / (that: Vector[S]): Vector[S] = {
    val out = new Vector(size)
    out << this
    out /= that
    return out
  }

  override def toString(): String = {
    data.map(_.toString()).toList.toString()
  }

}

object Vector {
  import core.math.linalg.Component.*

  // Factory Methods
  def apply(v1: Double)(using component: Component[_1]): Vector[_1] = {
    val out = new Vector(component.min_size())
    out.set[_1](v1)
    out
  }

  def apply(v1: Double, v2: Double)(using
      component: Component[_2]
  ): Vector[_2] = {
    val out = new Vector(component.min_size())
    out.set[_1](v1)
    out.set[_2](v2)
    out
  }

  def apply(v1: Double, v2: Double, v3: Double)(using
      component: Component[_3]
  ): Vector[_3] = {
    val out = new Vector(component.min_size())
    out.set[_1](v1)
    out.set[_2](v2)
    out.set[_3](v3)
    out
  }

  def apply(v1: Double, v2: Double, v3: Double, v4: Double)(using
      component: Component[_4]
  ): Vector[_4] = {
    val out = new Vector(component.min_size())
    out.set[_1](v1)
    out.set[_2](v2)
    out.set[_3](v3)
    out.set[_4](v4)
    out
  }
}
