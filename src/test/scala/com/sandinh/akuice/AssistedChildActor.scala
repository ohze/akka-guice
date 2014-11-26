package com.sandinh.akuice

import javax.inject.{Named, Inject}
import akka.actor.Actor

class AssistedChildActor(foo: String, arg1: Int, arg2: String) extends Actor {
  def receive = {
    case msg: String =>
      sender() ! (self.path.name, foo, msg, arg1, arg2)
  }
}

object AssistedChildActor {
  class Factory @Inject() (@Named("fooName") foo: String) extends ActorFactory[AssistedChildActor] {
    def create(args: Any*): AssistedChildActor = args match {
      case Seq(arg1: Int, arg2: String) => new AssistedChildActor(foo, arg1, arg2)
      case _                            => throw new IllegalArgumentException
    }
  }
}
