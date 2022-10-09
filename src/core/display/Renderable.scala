package core.display

trait Renderable {
    def render(renderer: Renderer): Unit
}

import core.shape.Shape

object RenderableZOrder extends Ordering[Renderable] {
    def compare(self: Renderable, other: Renderable): Int = 
        (self, other) match
            case (s1: Shape, s2: Shape) => 
                s1.position.z compare s2.position.z
            case _ => 0
    end compare
}
