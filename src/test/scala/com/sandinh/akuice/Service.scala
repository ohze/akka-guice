package com.sandinh.akuice

import javax.inject.{Singleton, Inject}
import akka.actor.ActorRef
import com.google.inject.Injector

@Singleton
class Service @Inject() (val injector: Injector) extends ActorInject {
  private[this] val parentRef = injectTopActor[ParentActor]

  def hello(sender: ActorRef) = parentRef.tell("hello!", sender)
}
