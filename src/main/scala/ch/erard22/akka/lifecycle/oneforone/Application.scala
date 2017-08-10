package ch.erard22.akka.lifecycle.oneforone

import akka.actor._
import akka.event.Logging
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn
import scala.language.postfixOps

object Application extends App {

  override def main(args: Array[String]) {

    val system = ActorSystem("OneForOneSystem")

    val log = Logging(system, this.getClass)

    val oneForOneSupervisorActor = system.actorOf(Props[OneForOneSupervisorActor], name = "OneForOneSupervisorActor")

    log.info("sending oneForOneSupervisorActor a 'StartChildren' message")
    oneForOneSupervisorActor ! "StartChildren"
    Thread.sleep(1000)

    log.info("sending oneForOneSupervisorActor a 'MakeRandomChildRestart' message")
    oneForOneSupervisorActor ! "MakeRandomChildRestart"
    Thread.sleep(1000)

    log.info("sending oneForOneSupervisorActor a 'TellChildrenToSoundOff' message")
    oneForOneSupervisorActor ! "TellChildrenToSoundOff"
    Thread.sleep(1000)

    log.info("sending oneForOneSupervisorActor a 'MakeRandomChildComitSuicide' message")
    oneForOneSupervisorActor ! "MakeRandomChildCommitSuicide"
    Thread.sleep(1000)

    log.info("sending oneForOneSupervisorActor a 'TellChildrenToSoundOff' message")
    oneForOneSupervisorActor ! "TellChildrenToSoundOff"
    Thread.sleep(1000)

    system.stop(oneForOneSupervisorActor)
  }
}
