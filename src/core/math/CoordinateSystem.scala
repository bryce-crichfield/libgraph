package core.math

trait CoordinateSystem

trait Default extends CoordinateSystem
object Default {
    def default(): Vector[Default] = {
        Vector[Default](1,1,1,1)
    }
}

trait Color extends CoordinateSystem

trait Screen extends CoordinateSystem

trait Normalized extends Screen {
    def project[SELF <: Normalized]: 
        Projection[SELF, Normalized]
}

trait Local extends Normalized

trait Projection[A<: CoordinateSystem, B<: CoordinateSystem] {
  def apply(a: Vector[A]): Vector[B]
}



