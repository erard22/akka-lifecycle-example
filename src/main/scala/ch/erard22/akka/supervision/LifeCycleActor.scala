package ch.erard22.akka.supervision

import akka.actor.{Actor, ActorLogging, PoisonPill}

class LifeCycleActor extends Actor with ActorLogging {

  log.info("LifeCycleActor: constructor")

  override def preStart { log.info("LifeCycleActor: preStart") }

  override def postStop { log.info("LifeCycleActor: postStop") }

  override def preRestart(reason: Throwable, message: Option[Any]) {
    log.info("LifeCycleActor: preRestart")
    log.info(s"LifeCycleActor reason: ${reason.getMessage}")
    log.info(s"LifeCycleActor message: ${message.getOrElse("")}")
    super.preRestart(reason, message)
  }
  override def postRestart(reason: Throwable) {
    log.info("LifeCycleActor: postRestart")
    log.info(s"LifeCycleActor reason: ${reason.getMessage}")
    super.postRestart(reason)
  }
  def receive = {
    case PoisonPill => throw new Exception("RestartMessage seen")
    case _ => log.info("LifeCycleActor : got a message")
  }
}
