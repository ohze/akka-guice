package com.sandinh.akuice

import akka.actor.{Actor, IndirectActorProducer}
import com.google.inject.Injector

class ActorProducer[A <: Actor](injector: Injector, clazz: Class[A]) extends IndirectActorProducer {
  def actorClass = clazz
  def produce() = injector.getBinding(clazz).getProvider.get()
}

trait ActorFactory[A <: Actor] {
  def create(args: Any*): A
}

class AssistedActorProducer[A <: Actor](injector: Injector, clazz: Class[A],
                                        factoryClazz: Class[_ <: ActorFactory[A]], args: Seq[Any]) extends IndirectActorProducer {
  def actorClass = clazz
  def produce() = injector.getBinding(factoryClazz).getProvider.get().create(args: _*)
}
