name := "akka-livecycle-example"

version := "1.0"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.5.3"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion
)
        