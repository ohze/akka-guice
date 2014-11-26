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

      val foo = "[the fooName value]"
      receiveN(3) map {
        case (actorName: String, `foo`, "hello!")                           => ("fromChild", actorName)
        case (actorName: String, `foo`, "hello!", i: Integer, "arg2 value") => ("fromAssistedChild", i.toString)
      } should contain theSameElementsAs Seq(
        "fromChild" -> "$a", //auto generated actor name
        "fromChild" -> "child2",
        "fromAssistedChild" -> "1"
      )
    }
  }
}
