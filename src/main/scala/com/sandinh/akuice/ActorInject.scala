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

  protected def injectActor[A <: Actor, F <: ActorFactory[A]](args: Any*)(implicit factory: ActorRefFactory, tag: ClassTag[A], ftag: ClassTag[F]): ActorRef =
    factory.actorOf(Props(classOf[AssistedActorProducer[A]], injector, tag.runtimeClass, ftag.runtimeClass, args))

  protected def injectActor[A <: Actor, F <: ActorFactory[A]](name: String, args: Seq[Any])(implicit factory: ActorRefFactory, tag: ClassTag[A], ftag: ClassTag[F]): ActorRef =
    factory.actorOf(Props(classOf[AssistedActorProducer[A]], injector, tag.runtimeClass, ftag.runtimeClass, args), name)

  protected def injectTopActor[A <: Actor](implicit tag: ClassTag[A]): ActorRef =
    injector.getInstance(classOf[ActorSystem]).actorOf(Props(classOf[ActorProducer[A]], injector, tag.runtimeClass))

  protected def injectTopActor[A <: Actor](name: String)(implicit tag: ClassTag[A]): ActorRef =
    injector.getInstance(classOf[ActorSystem]).actorOf(Props(classOf[ActorProducer[A]], injector, tag.runtimeClass), name)

  protected def injectTopActor[A <: Actor, F <: ActorFactory[A]](args: Any*)(implicit tag: ClassTag[A], ftag: ClassTag[F]): ActorRef =
    injector.getInstance(classOf[ActorSystem]).actorOf(Props(classOf[AssistedActorProducer[A]], injector, tag.runtimeClass, ftag.runtimeClass, args))

  protected def injectTopActor[A <: Actor, F <: ActorFactory[A]](name: String, args: Seq[Any])(implicit tag: ClassTag[A], ftag: ClassTag[F]): ActorRef =
    injector.getInstance(classOf[ActorSystem]).actorOf(Props(classOf[AssistedActorProducer[A]], injector, tag.runtimeClass, ftag.runtimeClass, args), name)
}
