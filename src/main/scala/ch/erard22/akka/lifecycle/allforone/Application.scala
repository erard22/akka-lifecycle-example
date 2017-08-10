package ch.erard22.akka.lifecycle.allforone

import akka.actor._
import akka.event.Logging

import scala.language.postfixOps

object Application extends App {

  override def main(args: Array[String]) {

    val system = ActorSystem("AllForOneSystem")

    val log = Logging(system, this.getClass)

    val allForOneSupervisorActor = system.actorOf(Props[AllForOneSupervisorActor], name = "OneForOneSupervisorActor")

    log.info("sending allForOneSupervisorActor a 'StartChildren' message")
    allForOneSupervisorActor ! "StartChildren"
    Thread.sleep(1000)

    log.info("sending allForOneSupervisorActor a 'MakeRandomChildRestart' message")
    allForOneSupervisorActor ! "MakeRandomChildRestart"
    Thread.sleep(1000)

    log.info("sending allForOneSupervisorActor a 'TellChildrenToSoundOff' message")
    allForOneSupervisorActor ! "TellChildrenToSoundOff"
    Thread.sleep(1000)

    log.info("sending allForOneSupervisorActor a 'MakeRandomChildComitSuicide' message")
    allForOneSupervisorActor ! "MakeRandomChildCommitSuicide"
    Thread.sleep(1000)

    log.info("sending allForOneSupervisorActor a 'TellChildrenToSoundOff' message")
    allForOneSupervisorActor ! "TellChildrenToSoundOff"
    Thread.sleep(1000)

    system.stop(allForOneSupervisorActor)
  }
}
