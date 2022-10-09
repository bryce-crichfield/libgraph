package test.state
import core.display.Renderable
import core.state.*
import core.shape.*
import core.math.Vector

case class PrintEvent(message: String) extends UnitModelEvent {
  def update(model: Model): Unit =
    if message.isEmpty() then ()
    else println(f"Print Event: $message")
    model match
      case t: TestModel =>
        println(t.message)
      case _ => ()
  end update

}

case class AddEvent(addend: Int) extends UnitModelEvent {
    def update(model: Model): Unit = 
        model match
            case t: TestModel => t.message += 1
            case _ => ()
    end update
}
case class Publish() extends ModelEvent[Renderable] {
    def update(model: Model): Renderable = 
        val out = new Circle()
        out.position = Vector(0, 0)
        out.radius = 0.1
        out.color = Vector(1, 0, 0)
        return out
}

