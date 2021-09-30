package com.sandinh.akuice

import javax.inject.Inject
import akka.actor.{Props, Actor}
import com.google.inject.Injector

class ParentActor @Inject() (
    val injector: Injector,
    fact: AssistedChildActor.Factory
) extends Actor
    with ActorInject {
  private val child1 = injectActor[ChildActor]
  private val child2 = injectActor[ChildActor]("child2")
  private val assistedChild = injectActor(fact(1, "arg2 value"))
  private val assistedChild2 =
    injectActor(fact(2, "arg2 value"), "assistedChild2")

  def receive = { case x =>
    child1 forward x
    child2 forward x
    assistedChild forward x
    assistedChild2 forward x
  }
}
