ThisBuild / scalaVersion := "3.2.0"

lazy val libgraph = (project in file("."))
  .settings(
    name := "libgraph",
    Compile / scalaSource := baseDirectory.value / "src",
    Test    / scalaSource := baseDirectory.value / "test"
  )
