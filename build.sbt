enablePlugins(JmhPlugin)

name := "scala.jdbc"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1"
libraryDependencies ++= {
  val scalikejdbcVersion = "4.0.0"
  Seq(
    "com.h2database" % "h2" % "2.2.224",
    "com.typesafe" % "config" % "1.4.2",
    "ch.qos.logback" % "logback-classic" % "1.4.11",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)