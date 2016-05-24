organization := "com.sandinh"

name := "akka-guice"

version := "3.1.2"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.0-M4")

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature", "-target:jvm-1.8")
scalacOptions ++= (CrossVersion.scalaApiVersion(scalaVersion.value) match {
  case Some((2, 11)) => Seq("-Ybackend:GenBCode")
  case _ => Nil
})

libraryDependencies ++= Seq(
  "com.google.inject.extensions" % "guice-assistedinject" % "4.0",
  "com.typesafe.akka"   %% "akka-actor"   % "2.4.6",
  "com.typesafe.akka"   %% "akka-testkit" % "2.4.6" % Test
)

libraryDependencies += "org.scalatest" %% "scalatest" % (CrossVersion.scalaApiVersion(scalaVersion.value) match {
  case Some((2, 11)) => "3.0.0-RC1"
  case _ => "3.0.0-M16-SNAP4"
}) % Test
