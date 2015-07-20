## Changelogs
we use [Semantic Versioning](http://semver.org/)

##### v2.0.1
update scala 2.11.5, akka 2.3.8

##### v2.0.0
remove `trait TopActorInject`. use `injectTopActor` method to inject top actor

##### v1.2.0
+ cross compile to scala 2.10.4 & 2.11.4
+ make `TopActorInject.actorSystem` a `final def` to fix [a invalid error highlight in Intellij](https://youtrack.jetbrains.com/issue/SCL-7924)

##### v1.1.0
 Support Assisted Inject Actor

##### v1.0.0
 First release