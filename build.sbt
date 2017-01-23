organization := "com.sandinh"

name := "akka-guice"

version := "3.1.3"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.1")

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature", "-target:jvm-1.8")
scalacOptions ++= (CrossVersion.scalaApiVersion(scalaVersion.value) match {
  case Some((2, 11)) => Seq("-Ybackend:GenBCode")
  case _ => Nil
})

libraryDependencies ++= Seq(
  "com.google.inject.extensions" % "guice-assistedinject" % "4.0",
  "com.typesafe.akka"   %% "akka-actor"   % "2.4.16",
  "com.typesafe.akka"   %% "akka-testkit" % "2.4.16" % Test,
  "org.scalatest"       %% "scalatest"    % "3.0.1"  % Test
)
