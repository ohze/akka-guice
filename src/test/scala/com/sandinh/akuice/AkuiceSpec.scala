package com.sandinh.akuice

import akka.actor._
import akka.testkit.{ImplicitSender, TestKit}
import com.google.inject.Guice
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.matchers.should.Matchers

class AkuiceSpec
    extends TestKit(ActorSystem("foo"))
    with ImplicitSender
    with AnyWordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  override def afterAll() = TestKit.shutdownActorSystem(system)

  "Akuice" must {
    "inject actors: receive replied messages when call Service.hello" in {
      val injector = Guice.createInjector(new AkkaModule)
      val service = injector.getInstance(classOf[Service])

      service.hello(self)

      val foo = "[the fooName value]"
      receiveN(4) map {
        case (actorName: String, `foo`, "hello!") =>
          "fromChild" -> actorName //message from ChildActor
        case (actorName: String, `foo`, "hello!", i: Integer, "arg2 value") =>
          i -> actorName //message from AssistedChildActor
      } should contain theSameElementsAs Seq(
        "fromChild" -> "$a", //"$a" is auto generated actor name
        "fromChild" -> "child2",
        1 -> "$b", //"$b" auto generated actor name
        2 -> "assistedChild2"
      )
    }
  }
}
