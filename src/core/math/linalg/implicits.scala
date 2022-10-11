package core.math.linalg

import Component.{_1, _2, _3, _4}

object implicits {
  given Component[_1] with
    override def min_size(): Int = 1
  given Component[_2] with
    override def min_size(): Int = 2
  given Component[_3] with
    override def min_size(): Int = 3
  given Component[_4] with
    override def min_size(): Int = 4
  // Allows the user to cast a vector up or down
  extension (vector: Vector[_1])
    @scala.annotation.targetName("Grow2")
    def grow(value: Double)(using size: Component[_2]): Vector[_2] = {
      Vector(vector.get[_1], value)
    }
  extension (vector: Vector[_2])
    @scala.annotation.targetName("Grow3")
    def grow(value: Double)(using size: Component[_3]): Vector[_3] = {
      Vector(vector.get[_1], vector.get[_2], value)
    }
    @scala.annotation.targetName("Shrink1")
    def shrink(using size: Component[_1]): Vector[_1] = {
      Vector(vector.get[_1])
    }
  extension (vector: Vector[_3])
    @scala.annotation.targetName("Grow4")
    def grow(value: Double)(using size: Component[_4]): Vector[_4] = {
      Vector(vector.get[_1], vector.get[_2], vector.get[_3], value)
    }
    @scala.annotation.targetName("Shrink2")
    def shrink(using size: Component[_2]): Vector[_2] = {
      Vector(vector.get[_1], vector.get[_2])
    }
  extension (vector: Vector[_4])
    @scala.annotation.targetName("Shrink3")
    def shrink(using size: Component[_3]): Vector[_3] = {
      Vector(vector.get[_1], vector.get[_2], vector.get[_3])
    }
}
