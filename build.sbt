organization := "com.sandinh"

name := "akka-guice"

version := "3.1.1"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.11.7", "2.12.0-M3")

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature", "-target:jvm-1.8", "-Ybackend:GenBCode")

libraryDependencies ++= Seq(
  "com.google.inject.extensions" % "guice-assistedinject" % "4.0",
  "com.typesafe.akka"   %% "akka-actor"   % "2.4.2",
  "com.typesafe.akka"   %% "akka-testkit" % "2.4.2"     % Test,
  "org.scalatest"       %% "scalatest"    % "3.0.0-M12" % Test
)

//misc - to mute intellij warning when load sbt project
dependencyOverrides ++= Set(
  "org.scala-lang.modules"  %% "scala-xml"                % "1.0.5",
  "org.scala-lang"          % "scala-reflect"             % scalaVersion.value
)
