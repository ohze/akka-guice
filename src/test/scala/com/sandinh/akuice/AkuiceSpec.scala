package com.sandinh.akuice

import akka.actor._
import akka.testkit.{ImplicitSender, TestKit}
import com.google.inject.Guice
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class AkuiceSpec(system: ActorSystem) extends TestKit(system)
    with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {
  def this() = this(ActorSystem("foo"))

  override def afterAll() = TestKit.shutdownActorSystem(system)

  "Akuice" must {
    "inject actors: receive replied messages when call Service.hello" in {
      val injector = Guice.createInjector(new AkkaModule(system))
      val service = injector.getInstance(classOf[Service])
      service.hello(self)

      expectMsgPF() {
        case (path: String, "[the fooName value]", "hello!") //message from ParentActor.child2
        if path.endsWith("/child2") ||
          //message from ParentActor.child1
          path.endsWith("/$a/$a") => "ok"

        //message from ParentActor.assistedChild
        case (path: String, "[the fooName value]", "hello!", i: Integer, "arg2 value") if i == 1 => "ok"
      }
    }
  }
}
