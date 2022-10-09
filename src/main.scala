object TestMain extends core.app.Application {
    val state = test.state.TestStateLayer()
    val display = jawt.AWTDisplay()
    val clock = core.app.Clock(30)

    display.setSize(400, 400)
    run()
}