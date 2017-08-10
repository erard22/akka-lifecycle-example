package ch.erard22.akka.lifecycle.allforone

import akka.actor.SupervisorStrategy.Directive
import akka.actor._
import ch.erard22.akka.lifecycle.LifeCycleActor
import ch.erard22.akka.lifecycle.oneforone.OneForOneSupervisorActor.{AkkaRestartDirectiveException, AkkaStopDirectiveException}

import scala.util.Random

class AllForOneSupervisorActor extends Actor {
  println("AllForOneSupervisorActor: constructor")

  val lifeCycleChildrenActors = new Array[ActorRef](3)
  val random = new Random()

  def receive = {
    case "StartChildren" =>
      println(s"AllForOneSupervisorActor : got a message StartChildren")

      for(i <- 0 to 2) {
        val child = context.actorOf(Props[LifeCycleActor], name = s"lifecycleactor_$i")
        lifeCycleChildrenActors(i) = child
      }

    case "MakeRandomChildCommitSuicide" =>
      println(s"AllForOneSupervisorActor : got a message MakeRandomChildCommitSuicide")
      lifeCycleChildrenActors(random.nextInt(3)) ! "stop"

    case "MakeRandomChildRestart" =>
      println(s"AllForOneSupervisorActor : got a message MakeRandomChildRestart")
      lifeCycleChildrenActors(random.nextInt(3)) ! "restart"

    case "TellChildrenToSoundOff" =>
      println(s"AllForOneSupervisorActor : got a message TellChildrenToSoundOff")

      lifeCycleChildrenActors.foreach(x => x ! "SoundOff")
  }

  val decider: PartialFunction[Throwable, Directive] = {
    case _: AkkaStopDirectiveException =>
      akka.actor.SupervisorStrategy.stop
    case _: AkkaRestartDirectiveException =>
      akka.actor.SupervisorStrategy.restart
  }

  override def supervisorStrategy: SupervisorStrategy =
    AllForOneStrategy()(decider.orElse(
      SupervisorStrategy.defaultStrategy.decider))
}
