akka-guice
==========
[![CI](https://github.com/ohze/akka-guice/actions/workflows/sbt-devops.yml/badge.svg)](https://github.com/ohze/akka-guice/actions/workflows/sbt-devops.yml)

## What?

This is a very simple (so very stable) scala library for injecting [Akka](http://akka.io/) using [Guice](https://github.com/google/guice/).

## Why?
[Google search akka guice](https://www.google.com.vn/search?q=akka+guice) => some articles:

+ [Akka Dependency Injection](http://letitcrash.com/post/55958814293/akka-dependency-injection) => just a guide.

+ [Akka Scala Guice Activator](http://typesafe.com/activator/template/activator-akka-scala-guice) => just a (complex) demo.

+ [Akka Guice Integration for Playframework](https://github.com/chanan/AkkaGuice) => I think akka-guice integration should
  NOT depends on Play!

=> So I create this library :D

## How?

1. install [akka-guice from maven center](http://search.maven.org/#search|ga|1|g%3A%22com.sandinh%22%20akka-guice)
 ex, add to build.sbt:
  `libraryDependencies += "com.sandinh" %% "akka-guice" % "3.2.0"`

2. use akka-guice

see [the test files](src/test/scala/com/sandinh/akuice) & source code for detail. It's very simple!

+ ChildActor: The `foo` parameter will be injected

```scala
class ChildActor @Inject() (@Named("fooName") foo: String) extends Actor ...
```

+ AssistedChildActor:
  Sometimes an Actor gets some of its constructor parameters from the Guice Injector and others from the caller.
  
  Parameter `foo` of AssistedChildActor's constructor is taken from Guice. `arg1` & `arg2` from the caller.

```scala
class AssistedChildActor(foo: String, arg1: Int, arg2: String) extends Actor ...

object AssistedChildActor {
  class Factory @Inject() (@Named("fooName") foo: String) extends ActorFactory[AssistedChildActor] {
    def create(args: Any*): AssistedChildActor = args match {
      case Seq(arg1: Int, arg2: String) => new AssistedChildActor(foo, arg1, arg2)
      case _                            => throw new IllegalArgumentException
    }
  }
}
```

+ ParentActor: `extends com.sandinh.akuice.ActorInject` & use `injectActor` method to inject child actors.
  The `injectActor` method need an `implicit akka.actor.ActorRefFactory` to create actor (using ActorRefFactory#actorOf method).
  
  `ParentActor extends Actor` => it has an `implicit val context: ActorContext` (`ActorContext` extends `ActorRefFactory`).

```scala
class ParentActor @Inject() (val injector: Injector) extends Actor with ActorInject {
  private val child1 = injectActor[ChildActor]
  private val child2 = injectActor[ChildActor]("child2")
  private val assistedChild = injectActor[AssistedChildActor, AssistedChildActor.Factory](1, "arg2 value")
  ...
}
```

+ Service.scala: This is not an Actor but because it extends `com.sandinh.akuice.ActorInject` =>
 we can use `injectTopActor` method.

  Note that, we can also use `injectActor` method. The injected actor will be a child of
   the `implicit ActorRefFactory` in the scope.

```scala
@Singleton
class Service @Inject() (val injector: Injector) extends ActorInject {
  private val parentRef = injectTopActor[ParentActor]

  def hello(sender: ActorRef) = parentRef.tell("hello!", sender)
}
```

+ AkkaModule:

```scala
class AkkaModule(system: ActorSystem) extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[ActorSystem]).toInstance(system)
    //other binds
  }
}
```

+ AkuiceSpec:

```scala
"Akuice" must {
    "inject actors: receive replied messages when call Service.hello" in {
      val injector = Guice.createInjector(new AkkaModule(system))
      val service = injector.getInstance(classOf[Service])
      service.hello(self)

      //expectMsg ...
    }
  }
```

## Changelogs
see [CHANGES.md](CHANGES.md)

## Licence
This software is licensed under the Apache 2 license:
http://www.apache.org/licenses/LICENSE-2.0

Copyright 2014-2017 Sân Đình (http://sandinh.com)
