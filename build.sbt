enablePlugins(JmhPlugin)

name := "scala.jdbc"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.8.0-RC1"
libraryDependencies ++= {
  Seq(
    "com.h2database" % "h2" % "2.4.240",
    "com.typesafe" % "config" % "1.4.3",
    "ch.qos.logback" % "logback-classic" % "1.5.21",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
