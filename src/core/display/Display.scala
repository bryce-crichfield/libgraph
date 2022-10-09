package core.display
import scala.collection.mutable.{PriorityQueue as ZBuffer}
import core.math.Vector

trait Display {
    def render(renderables: ZBuffer[Renderable]): Unit
    def input(): InputAdapter
    def start(): Unit
    def dispose(): Unit 
    def setSize(w: Int, h: Int): Unit
    def getSize(): Vector
    def running(): Boolean
}

