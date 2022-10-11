package core.math.linalg

trait Component[S] {
  def min_size(): Int
}

object Component {
  type _1
  type _2 <: _1
  type _3 <: _2
  type _4 <: _3

  type X = _1
  type Y = _2
  type Z = _3
  type W = _4

  type R = _1
  type G = _2
  type B = _3
  type A = _4
}
