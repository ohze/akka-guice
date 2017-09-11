organization := "com.sandinh"

name := "akka-guice"

version := "3.2.0"

scalaVersion := "2.12.3"

crossScalaVersions := Seq("2.11.11", "2.12.3")

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature", "-target:jvm-1.8")
scalacOptions ++= (CrossVersion.scalaApiVersion(scalaVersion.value) match {
  case Some((2, 11)) => Seq("-Ybackend:GenBCode")
  case _ => Nil
})

libraryDependencies ++= Seq(
  "com.google.inject.extensions" % "guice-assistedinject" % "4.1.0",
  "com.typesafe.akka"   %% "akka-actor"   % "2.5.4",
  "com.typesafe.akka"   %% "akka-testkit" % "2.5.4" % Test
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test
