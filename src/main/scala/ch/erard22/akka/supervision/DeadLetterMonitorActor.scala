package ch.erard22.akka.supervision

import akka.actor.{Actor, DeadLetter}

class DeadLetterMonitorActor
  extends Actor
    with akka.actor.ActorLogging {
  log.info("DeadLetterMonitorActor: constructor")

  override def preStart { log.info("DeadLetterMonitorActor: preStart") }

  override def postStop { log.info("DeadLetterMonitorActor: postStop") }

  override def preRestart(reason: Throwable, message: Option[Any]) {
    log.info("DeadLetterMonitorActor: preRestart")
    log.info(s"DeadLetterMonitorActor reason: ${reason.getMessage}")
    log.info(s"DeadLetterMonitorActor message: ${message.getOrElse("")}")
    super.preRestart(reason, message)
  }
  override def postRestart(reason: Throwable) {
    log.info("DeadLetterMonitorActor: postRestart")
    log.info(s"DeadLetterMonitorActor reason: ${reason.getMessage}")
    super.postRestart(reason)
  }

  def receive = {
    case d: DeadLetter => {
      log.error(s"DeadLetterMonitorActor : saw dead letter $d")
    }
    case _ => log.info("DeadLetterMonitorActor : got a message")
  }
}
