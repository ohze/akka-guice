package com.sandinh.akuice

import javax.inject.{Named, Inject}
import akka.actor.Actor
import com.google.inject.assistedinject.Assisted

class AssistedChildActor @Inject() (
    @Named("fooName") foo: String,
    @Assisted arg1: Int,
    @Assisted arg2: String
) extends Actor {
  def receive = { case msg: String =>
    sender() ! (self.path.name, foo, msg, arg1, arg2)
  }
}

object AssistedChildActor {
  trait Factory {
    def apply(arg1: Int, arg2: String): Actor
  }
}
