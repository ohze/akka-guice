import AkkaAxis._

lazy val `akka-guice` = projectMatrix
  .in(file("."))
  .customRow(
    scalaVersions = Seq(scala211, scala212, scala213),
    axisValues = Seq(akka25, VirtualAxis.jvm),
    Seq(
      moduleName := name.value + "_2_5",
      libraryDependencies ++= Seq(
        akka25.module("actor"),
        akka25.module("testkit") % Test,
        "com.google.inject.extensions" % "guice-assistedinject" % "4.1.0",
      ),
    ),
  )
  .customRow(
    scalaVersions = Seq(scala212, scala213, scala3),
    axisValues = Seq(akka26, VirtualAxis.jvm),
    Seq(
      moduleName := name.value,
      libraryDependencies ++= Seq(
        akka26.module("actor"),
        akka26.module("testkit") % Test,
        "com.google.inject.extensions" % "guice-assistedinject" % "4.2.3",
      ),
    ),
  )
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    scalacOptions := {
      val old = scalacOptions.value
      CrossVersion.scalaApiVersion(scalaVersion.value) match {
        case Some((2, n)) if n > 11 =>
          old :+ raw"-Wconf:msg=isAccessible in class AccessibleObject:i"
        // TODO remove for scala3 when the following PR is released
        // https://github.com/lampepfl/dotty/pull/12857
        case _ => old.filterNot(_ == "-Xfatal-warnings")
      }
    },
  )

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

// we need this aggregating project only because
// `mqtt-proto`'s source is NOT in a subdirectory
lazy val root = Project("akka-guice", file("."))
  .settings(skipPublish)
  .settings(
    Compile / unmanagedSourceDirectories := Nil,
    Test / unmanagedSourceDirectories := Nil
  )
  .aggregate(`akka-guice`.projectRefs: _*)
