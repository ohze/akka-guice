organization := "com.sandinh"

name := "akka-guice"

version := "2.0.1"

scalaVersion := "2.11.5"

crossScalaVersions := Seq("2.11.5", "2.10.4")

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-feature", "-Xfuture", //"–Xverify", "-Xcheck-null",
  "-Ywarn-dead-code", "-Ydead-code", "-Yinline-warnings" //"-Yinline", "-Ystatistics",
)

libraryDependencies ++= {
  val guice     = "3.0"
  val ak        = "2.3.8"
  val scalatest = "2.2.3"
  Seq(
    "com.google.inject"   % "guice"         % guice,
    "com.typesafe.akka"   %% "akka-actor"   % ak,
    "org.scalatest"       %% "scalatest"    % scalatest % "test",
    "com.typesafe.akka"   %% "akka-testkit" % ak        % "test"
  )
}
