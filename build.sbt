organization := "com.sandinh"

name := "akka-guice"

version := "2.1.0-SNAPSHOT"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.11.7", "2.10.5")

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-feature", "-Xfuture", //"–Xverify", "-Xcheck-null",
  "-Ywarn-dead-code", "-Ydead-code", "-Yinline-warnings" //"-Yinline", "-Ystatistics",
)

libraryDependencies ++= Seq(
  "com.google.inject"   % "guice"         % "4.0",
  "com.typesafe.akka"   %% "akka-actor"   % "2.3.12",
  "org.scalatest"       %% "scalatest"    % "2.2.5" % Test,
  "com.typesafe.akka"   %% "akka-testkit" % "2.3.12"  % Test
)
