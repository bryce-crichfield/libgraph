package test.state

import core.state.{InputResponder, StateLayer}

class TestStateLayer extends StateLayer {
    val responder = InputResponder(TestResponseMode())
    val model = TestModel()
}
