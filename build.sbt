organization := "com.sandinh"

name := "akka-guice"

version := "3.0.0"

scalaVersion := "2.11.7"

//crossScalaVersions := Seq("2.11.7", "2.10.5")

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-feature", "-Xfuture", //"–Xverify", "-Xcheck-null",
  "-Ywarn-dead-code", "-Ydead-code", "-Yinline-warnings" //"-Yinline", "-Ystatistics",
)

libraryDependencies ++= Seq(
  "com.google.inject.extensions" % "guice-assistedinject" % "4.0",
  "com.typesafe.akka"   %% "akka-actor"   % "2.4.0",
  "org.scalatest"       %% "scalatest"    % "2.2.5" % Test,
  "com.typesafe.akka"   %% "akka-testkit" % "2.4.0" % Test
)

//misc - to mute intellij warning when load sbt project
dependencyOverrides ++= Set(
  "org.scala-lang.modules"  %% "scala-xml"                % "1.0.5",
  "org.scala-lang"          % "scala-reflect"             % scalaVersion.value
)
