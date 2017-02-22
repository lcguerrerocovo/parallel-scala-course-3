name := "parallel-programming-coursework"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies  ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)

// for debugging sbt problems
logLevel := Level.Debug

scalacOptions += "-deprecation"
