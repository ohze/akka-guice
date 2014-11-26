package com.sandinh.akuice

import javax.inject.Inject
import akka.actor.Actor
import com.google.inject.Injector

class ParentActor @Inject() (val injector: Injector) extends Actor with ActorInject {
  private val child1 = injectActor[ChildActor]
  private val child2 = injectActor[ChildActor]("child2")
  private val assistedChild = injectActor[AssistedChildActor, AssistedChildActor.Factory](1, "arg2 value")
  private val assistedChild2 = injectActor[AssistedChildActor, AssistedChildActor.Factory]("assistedChild2", Seq(2, "arg2 value"))

  def receive = {
    case x =>
      child1 forward x
      child2 forward x
      assistedChild forward x
      assistedChild2 forward x
  }
}
