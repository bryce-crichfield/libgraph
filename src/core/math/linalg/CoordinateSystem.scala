package core.math.linalg

trait Coordinate
object Coordinate {
    type Identity <: Coordinate
    type Screen   <: Coordinate
    type Unit     <: Coordinate

    // Via Type Composition, the client can compose new vector 
    // defintions, to add additional systems as seen fit
    type V[S <: Coordinate] = S match
        case Coordinate.Identity => Vector[Component._4]
        case Coordinate.Screen   => Vector[Component._3]
        case Coordinate.Unit     => Vector[Component._3]
        case _                   => Vector[Component._4]
}

trait Projection[V[_], A <: Coordinate, B <: Coordinate] {
    def apply(a: V[A]): V[B]
}

object SampleImport {
    import core.math.linalg.Coordinate.{V => Vector}
    import core.math.linalg.Coordinate.{Identity, Screen, Unit}
}