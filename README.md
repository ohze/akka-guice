akka-guice
==========

## What?

This is a simple scala library for injecting [Akka](http://akka.io/) using [Guice](https://github.com/google/guice/).

## Why?
[Google search akka guice](https://www.google.com.vn/search?q=akka+guice) => some articles:

+ [Akka Dependency Injection](http://letitcrash.com/post/55958814293/akka-dependency-injection) => just a guide.

+ [Akka Scala Guice Activator](http://typesafe.com/activator/template/activator-akka-scala-guice) => just a (complex) demo.

=> So I create this library :D

## How?

1. install [akka-guice from maven center](http://search.maven.org/#search|ga|1|g%3A%22com.sandinh%22%20akka-guice)

2. see [the test files](src/test/scala/com/sandinh/akuice)

+ ChildActor: The `foo` parameter will be injected

```scala
class ChildActor @Inject() (@Named("fooName") foo: String) extends Actor {
  def receive = {
    case msg: String => sender() ! (self.path.toString, foo, msg)
  }
}
```

+ AssistedChildActor:
  Sometimes an Actor gets some of its constructor parameters from the Guice Injector and others from the caller.
  
  Parameter `foo` of AssistedChildActor's constructor is taken from Guice. `arg1` & `arg2` from the caller.

```scala
class AssistedChildActor(foo: String, arg1: Int, arg2: String) extends Actor {
  def receive = {
    case msg: String => sender() ! (self.path.toString, foo, msg, arg1, arg2)
  }
}

object AssistedChildActor {
  @Singleton
  class Factory @Inject() (@Named("fooName") foo: String)
    extends ActorFactory[AssistedChildActor]
  {
    def create(args: Any*): AssistedChildActor = args match {
      case Seq(arg1: Int, arg2: String) => new AssistedChildActor(foo, arg1, arg2)
      case _                            => throw new IllegalArgumentException
    }
  }
}
```

+ ParentActor: extends com.sandinh.akuice.ActorInject & use injectActor method to inject child actors.
  The `injectActor` method need an implicit akka.actor.ActorRefFactory to create actor (using ActorRefFactory#actorOf method).
  
  ParentActor extends Actor => it has an implicit val context: ActorContext (ActorContext extends ActorRefFactory).

```scala
class ParentActor @Inject() (val injector: Injector) extends Actor with ActorInject {
  private[this] val child1 = injectActor[ChildActor]
  private[this] val child2 = injectActor[ChildActor]("child2")
  private[this] val assistedChild =
    injectActor[AssistedChildActor, AssistedChildActor.Factory](1, "arg2 value")

  def receive = {
    case x =>
      child1 forward x
      child2 forward x
      assistedChild forward x
  }
}
```

+ Service.scala: This is not an Actor but because it extends com.sandinh.akuice.TopActorInject => we can also using injectActor method.

  Note that, the parentRef actor is a directly child of ActorSystem.

```scala
@Singleton
class Service @Inject() (val injector: Injector) extends TopActorInject {
  private[this] val parentRef = injectActor[ParentActor]

  def hello(sender: ActorRef) = parentRef.tell("hello!", sender)
}
```

+ AkkaModule:

```scala
class AkkaModule(system: ActorSystem) extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[ActorSystem]).toInstance(system)
    bindConstant().annotatedWith(Names.named("fooName")).to("[the fooName value]")
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

      expectMsgPF() {
        case (path: String, "[the fooName value]", "hello!")
          //message from ParentActor.child2
          if path.endsWith("/child2") ||
            //message from ParentActor.child1
            path.endsWith("/$a/$a") => "ok"

        //message from ParentActor.assistedChild
        case (path: String, "[the fooName value]", "hello!", i: Integer, "arg2 value")
          if i == 1 => "ok"
      }
    }
  }
```

## Licence
This software is licensed under the Apache 2 license:
http://www.apache.org/licenses/LICENSE-2.0

Copyright 2014 Sân Đình (http://sandinh.com)
