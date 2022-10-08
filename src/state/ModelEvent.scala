package state

import graphics.Renderable
import shape.Circle

trait ModelEvent[O] {
  def update(model: Model): O
}

trait UnitModelEvent extends ModelEvent[Unit] {
    def update(model: Model): Unit 
}

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
        out.position = math.Vector(0, 0)
        out.radius = 0.1
        out.color = math.Vector(1, 0, 0)
        return out
}
