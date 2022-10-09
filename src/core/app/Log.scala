package core.app

import java.util.concurrent.{LinkedBlockingQueue as Buffer}

object Logger extends Thread {
    val buffer = new Buffer[String]()
    val clock = Clock(5)

    def apply(message: String): Unit = synchronized {
        buffer.put(message)
    } 

    override def start(): Unit = {
        while (true) {
            if (clock.tick()) {
                buffer.forEach { msg => println(msg) }
                buffer.clear()
            }
        }
    }

}
