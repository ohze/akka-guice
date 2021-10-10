lazy val `akka-guice` = projectMatrix
  .in(file("."))
  .akkaAxis(akka25, Seq(scala211, scala212, scala213))
  .akkaAxis(akka26, Seq(scala212, scala213, scala3))
  .settings(
    libraryDependencies ++= Seq(
      "com.google.inject.extensions" % "guice-assistedinject" % (akkaAxis.value match {
        // To keep compatible with akka-guid:3.2.0. TODO update
        case `akka25` => "4.1.0"
        case _        => "4.2.3"
      }),
      "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    ) ++ akka("actor", "testkit" -> Test).value,
    scalacOptions := {
      val old = scalacOptions.value
      CrossVersion.scalaApiVersion(scalaVersion.value) match {
        case Some((2, n)) if n > 11 =>
          old :+ raw"-Wconf:msg=isAccessible in class AccessibleObject:i"
        case _ => old
      }
    },
    // opens java.base/java.lang for java 16+ to workaround error:
    // InaccessibleObjectException: Unable to make protected final java.lang.Class
    //  java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
    //  throws java.lang.ClassFormatError accessible:
    //  module java.base does not "opens java.lang" to unnamed module @42a6eabd (ReflectUtils.java:61)
    addOpensForTest(),
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
