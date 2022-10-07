ThisBuild / scalaVersion := "3.2.0"


lazy val bvm = (project in file("."))
  .settings(
    name := "libgraph",
    Compile / javaSource := baseDirectory.value / "src",
    Compile / scalaSource := baseDirectory.value / "src",
    Test / scalaSource    := baseDirectory.value / "test",
  )