// so we can update sd-devops-oss soon after its releasing new version
resolvers += Resolver.sonatypeRepo("public")

addSbtPlugin("com.sandinh" % "sd-devops-oss" % "5.0.1")

addSbtPlugin("com.eed3si9n" % "sbt-projectmatrix" % "0.8.0")
