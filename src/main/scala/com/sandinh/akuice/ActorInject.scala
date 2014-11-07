package com.sandinh.akuice

import akka.actor._
import com.google.inject.Injector
import scala.reflect.ClassTag

trait ActorInject {
  protected def injector: Injector

  protected def injectActor[A <: Actor](implicit factory: ActorRefFactory, tag: ClassTag[A]): ActorRef =
    factory.actorOf(Props(classOf[ActorProducer[A]], injector, tag.runtimeClass))

  protected def injectActor[A <: Actor](name: String)(implicit factory: ActorRefFactory, tag: ClassTag[A]): ActorRef =
    factory.actorOf(Props(classOf[ActorProducer[A]], injector, tag.runtimeClass), name)
}

trait TopActorInject extends ActorInject {
  implicit protected def actorSystem: ActorSystem = injector.getInstance(classOf[ActorSystem])
}
