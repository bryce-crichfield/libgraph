import core.math.SizedVector
object TestMain extends core.app.Application {
    val state = test.state.TestStateLayer()
    val display = jawt.AWTDisplay()
    val clock = core.app.Clock(30)

    import core.math.SizedVector.*

    val a = SizedVector(1.0, 2.0, 3.0)
    val b = a.shrink.shrink.grow(1.0d)
    println(a.shrink)

    // display.setSize(800, 800)
    // run()
}
