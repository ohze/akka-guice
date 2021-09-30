import AkkaAxis._

lazy val `akka-guice` = projectMatrix
  .in(file("."))
  .customRow(
    scalaVersions = Seq(scala211, scala212, scala213),
    axisValues = Seq(akka25, VirtualAxis.jvm),
    Seq(
      moduleName := name.value + "_2_5",
      akka25.depsSetting,
    ),
  )
  .customRow(
    scalaVersions = Seq(scala212, scala213, scala3),
    axisValues = Seq(akka26, VirtualAxis.jvm),
    Seq(
      moduleName := name.value,
      akka26.depsSetting,
    ),
  )
  .settings(
    libraryDependencies ++= Seq(
      "com.google.inject.extensions" % "guice-assistedinject" % "4.2.3",
      "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    )
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
