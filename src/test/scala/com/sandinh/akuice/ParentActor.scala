package com.sandinh.akuice

import javax.inject.Inject
import akka.actor.Actor
import com.google.inject.Injector

class ParentActor @Inject() (val injector: Injector) extends Actor with ActorInject {
  private[this] val child1 = injectActor[ChildActor]
  private[this] val child2 = injectActor[ChildActor]("child2")
  private[this] val assistedChild = injectActor[AssistedChildActor, AssistedChildActor.Factory](1, "arg2 value")

  def receive = {
    case x =>
      child1 forward x
      child2 forward x
      assistedChild forward x
  }
}
