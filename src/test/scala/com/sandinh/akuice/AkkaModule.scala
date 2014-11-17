package com.sandinh.akuice

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import com.google.inject.name.Names

class AkkaModule(system: ActorSystem) extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[ActorSystem]).toInstance(system)
    bindConstant().annotatedWith(Names.named("fooName")).to("[the fooName value]")
  }
}
