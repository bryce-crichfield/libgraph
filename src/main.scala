object Main extends App {
  val app = core.Application (
    state.GraphProgram(),
    jawt.AWTDisplay(),
    core.Clock(30)
  )
  app.display.setSize(400, 400)
  app.run()
}
  