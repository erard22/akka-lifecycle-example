package ch.erard22.akka.supervision

import akka.actor._
import akka.event.Logging
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn
import scala.language.postfixOps

object Application extends App with LazyLogging {

  override def main(args: Array[String]) {

    //create the actor system
    val system = ActorSystem("LifeCycleSystem")

    // default Actor constructor
    val lifeCycleActor =
      system.actorOf(Props[LifeCycleActor],
        name = "lifecycleactor")

    val deadLetterMonitorActor =
      system.actorOf(Props[DeadLetterMonitorActor],
        name = "deadlettermonitoractor")

    //subscribe to system wide event bus 'DeadLetter'
    system.eventStream.subscribe(
      deadLetterMonitorActor, classOf[DeadLetter])

    val log = Logging(system, this.getClass)
    log.info("Application IS UP BABY")

    log.info("sending lifeCycleActor a few numbers")
    lifeCycleActor ! 100
    lifeCycleActor ! 200
    Thread.sleep(1000)

    log.info("sending lifeCycleActor a poison pill (kill it)")
    lifeCycleActor ! PoisonPill
    Thread.sleep(1000)
    log.info("sending lifeCycleActor a few numbers")
    lifeCycleActor ! 100
    lifeCycleActor ! 200

    Thread.sleep(1000)
    log.info("stop lifeCycleActor/deadLetterMonitorActor")
    system.stop(lifeCycleActor)
    system.stop(deadLetterMonitorActor)

    //shutdown the actor system
    log.info("stop actor system")
    system.terminate()

    StdIn.readLine()
  }
}
