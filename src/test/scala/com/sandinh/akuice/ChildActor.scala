package com.sandinh.akuice

import javax.inject.Inject
import akka.actor.Actor
import com.google.inject.name.Named

class ChildActor @Inject() (@Named("fooName") foo: String) extends Actor {
  def receive = { case msg: String =>
    sender() ! (self.path.name, foo, msg)
  }
}
