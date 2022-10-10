package test.state

import core.state.{StateLayer, Container}
import core.math.Vector

def TestContainer(): Container = {
    val c1 = Container()
    c1.position = Vector.Vec2(0, .25)
    c1.size = Vector.Vec2(.1, .1)
    c1.color = Vector(1,1,0)
    
    val c2 = Container()
    c2.color = Vector(1,0,0)
    val c3 = Container()
    c3.color = Vector(0,1,0)

    c1.children.addOne(c2)
    c1.children.addOne(c3)
    c1
}


class TestStateLayer extends StateLayer {
    entities addOne (CircleInteractor())
    entities addOne (TestContainer())
}
