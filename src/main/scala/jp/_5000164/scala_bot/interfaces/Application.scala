package jp._5000164.scala_bot.interfaces

import akka.actor.ActorSystem
import slack.rtm.SlackRtmClient
import scala.concurrent.ExecutionContextExecutor

/**
  * アプリケーション起動
  */
object Application extends App {
  // Slack へ接続する
  implicit val system: ActorSystem = ActorSystem("slack")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  val token = sys.env("SLACK_TOKEN")
  val client = SlackRtmClient(token)

  // 動作準備をする
  val botId = client.state.self.id
  val operatableUserId = sys.env("OPERATABLE_USER_ID")
  val operator = new Operator(client, botId, operatableUserId, new Twitter)

  // メッセージを監視する
  client.onMessage(message => operator.run(message))
}
