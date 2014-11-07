package com.sandinh.akuice

import javax.inject._
import akka.actor._
import com.google.inject.{Injector, Guice, AbstractModule}
import com.google.inject.name.Names
import scala.io.StdIn

class ChildActor @Inject() (@Named("fooName") name: String) extends Actor {
  override def receive = {
    case x: String => println("msg from " + self.path.toString + name + ": " + x)
  }
}

class ParentActor @Inject() (val injector: Injector) extends Actor with ActorInject {
  private[this] val child1 = injectActor[ChildActor]
  private[this] val child2 = injectActor[ChildActor]("child2")

  override def receive = {
    case x => child1 ! x; child2 ! x
  }
}

class AkkaModule extends AbstractModule {
  def configure(): Unit = {
    val system = ActorSystem("foo")
    bind(classOf[ActorSystem]).toInstance(system)
    bindConstant().annotatedWith(Names.named("fooName")).to("[the fooName value]")
  }
}

@Singleton
class Service @Inject() (val injector: Injector) extends TopActorInject {
  private[this] val parentRef = injectActor[ParentActor]

  def hello() = parentRef ! "hello!"
}

object Main extends App {
  val injector = Guice.createInjector(new AkkaModule())
  val service = injector.getInstance(classOf[Service])
  service.hello()

  StdIn.readLine()

  val system = injector.getInstance(classOf[ActorSystem])
  system.shutdown()
  system.awaitTermination()
}
