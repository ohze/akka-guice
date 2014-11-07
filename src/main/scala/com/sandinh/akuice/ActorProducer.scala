package com.sandinh.akuice

import akka.actor.{Actor, IndirectActorProducer}
import com.google.inject.Injector

class ActorProducer[A <: Actor](injector: Injector, clazz: Class[A]) extends IndirectActorProducer {
  def actorClass = clazz
  def produce() = injector.getBinding(clazz).getProvider.get()
}
