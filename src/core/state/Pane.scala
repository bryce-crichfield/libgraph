package core.state

import core.display.Renderable
import core.display.Renderer
import core.math.Vector

import scala.collection.mutable.{ListBuffer => Buffer}
import java.awt.Rectangle

class Container extends Entity
{


    var position: Vector = Vector(0, 0)
    var size: Vector = Vector(.5, .5, 0)
    var color: Vector = Vector(1, 0, 0, 1)
    val children: Buffer[Container] = Buffer()
    val layout: Layout = VerticalLayout()

    def update(): Unit = layout.layout(this)

    def render(): Set[Renderable] = {
        val rectangle = core.shape.Rectangle()
        rectangle.position = position
        rectangle.width = size.x
        rectangle.height = size.y
        rectangle.color = color
        children.flatMap(_.render()).addOne(rectangle).toSet
        // Set()
    }

}

trait Layout {
    def layout(container: Container): Unit
}

class VerticalLayout extends Layout {
    def layout(container: Container): Unit = {
        val number = container.children.size
        val size_y = container.size.y / number
        var position = container.position
        var off_y = size_y
        val z = container.position.z - 1
        container.children.foreach { (child: Container) =>
            child.position = (position.z = z)
            child.size = Vector.Vec2(container.size.x, size_y)
            off_y += size_y
            position = Vector.Vec2(
                position.x, 
                position.y + off_y
            )  
        }
    }
}
