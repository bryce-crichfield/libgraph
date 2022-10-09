package core.display

import core.math.Vector

trait Display {
    def render(renderables: Set[Renderable]): Unit
    def input(): InputAdapter
    def start(): Unit
    def setSize(w: Int, h: Int): Unit
    def getSize(): Vector
}

