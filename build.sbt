organization := "com.sandinh"

name := "akka-guice"

version := "1.0.0"

scalaVersion := "2.11.4"

crossScalaVersions := Seq("2.11.4", "2.10.4")

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature", //"-optimise",
  "-Xmigration", "-Xfuture", //"–Xverify", "-Xcheck-null", "-Ystatistics",
  "-Yinline-warnings", //"-Yinline",
  "-Ywarn-dead-code", "-Ydead-code"
)

libraryDependencies ++= {
  val guice     = "3.0"
  val ak        = "2.3.6"
  val scalatest = "2.2.2"
  Seq(
    "com.google.inject"   % "guice"       % guice,
    "com.typesafe.akka"   %% "akka-actor" % ak,
    "org.scalatest"       %% "scalatest"  % scalatest % "test"
  )
}