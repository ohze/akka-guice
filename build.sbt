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
      "com.google.inject.extensions" % "guice-assistedinject" % "4.2.2",
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
