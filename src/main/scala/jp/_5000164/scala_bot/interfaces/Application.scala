package jp._5000164.scala_bot.interfaces

import akka.actor.ActorSystem
import jp._5000164.scala_bot.domain.Operator
import slack.rtm.SlackRtmClient
import scala.concurrent.ExecutionContextExecutor

object Application extends App {
  implicit val system: ActorSystem = ActorSystem("slack")
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val token = sys.env("SLACK_TOKEN")
  val client = SlackRtmClient(token)

  val botId = client.state.self.id
  val operatableUserId = sys.env("OPERATABLE_USER_ID")
  val operator = new Operator(new Twitter, botId, operatableUserId)

  client.onMessage { message =>
    operator.operate(message) match {
      case Some(result) => client.sendMessage(message.channel, result)
    }
  }
}
