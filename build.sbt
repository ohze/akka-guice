name := "akka-guice"

scalaVersion := scala213

crossScalaVersions := Seq(scala212, scala213)

libraryDependencies ++= Seq(
  "com.google.inject.extensions" % "guice-assistedinject" % "4.2.2",
  "com.typesafe.akka"   %% "akka-actor"   % "2.6.1",
  "com.typesafe.akka"   %% "akka-testkit" % "2.6.1" % Test
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test

inThisBuild(
  Seq(
    versionScheme := Some("semver-spec"),
    developers := List(
      Developer(
        "thanhbv",
        "Bui Viet Thanh",
        "thanhbv@sandinh.net",
        url("https://sandinh.com")
      ),
    ),
  )
)
