name := "Linear"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.1" % "provided",
  "org.apache.spark" %% "spark-streaming" % "2.1.1",
  "org.apache.spark" %% "spark-mllib" % "2.1.1")