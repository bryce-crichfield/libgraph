package core

class Clock(val fps: Int = 60) {
  val time_ns: Long = (1e9 / fps).toLong
  var lastTick: Long = 0
  def tick(): Boolean = {
    var current = System.nanoTime()
    if (current - lastTick > time_ns) then {
      lastTick = current
      true
    } else false
  }
}
