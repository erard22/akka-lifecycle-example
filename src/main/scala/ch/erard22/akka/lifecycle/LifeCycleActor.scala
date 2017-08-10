package ch.erard22.akka.lifecycle

import akka.actor.{Actor, ActorLogging}
import ch.erard22.akka.lifecycle.oneforone.OneForOneSupervisorActor.{AkkaRestartDirectiveException, AkkaStopDirectiveException}


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

    case "SoundOff" =>
      log.info("LifeCycleActor: SoundOff seen")
      log.info(s"LifeCycleActor alive ${self.path.name}" )

    case "stop" =>
      log.info("LifeCycleActor: RaiseStopThrowableMessage seen")
      throw AkkaStopDirectiveException(
        "LifeCycleActor raised AkkaStopDirectiveException")

    case "restart" =>
      log.info("LifeCycleActor: RaiseRestartThrowableMessage seen")
      throw AkkaRestartDirectiveException(
        "LifeCycleActor raised AkkaRestartDirectiveException")
  }
}
