package jp._5000164.scala_bot.interfaces

import jp._5000164.scala_bot.domain.{Command, TweetCommand}
import slack.SlackUtil
import slack.models.Message
import slack.rtm.SlackRtmClient

/**
  * 処理をコントロールする
  *
  * @param client           Slack に発言するため
  * @param botId            自身の発言を区別するため
  * @param operatableUserId 特定のユーザーだけが処理を行えるようにするため
  * @param twitter          Twitter に関する処理を行うため
  */
class Operator(val client: SlackRtmClient, val botId: String, val operatableUserId: String, val twitter: Twitter) {
  /**
    * メッセージに応じた処理を行う
    *
    * @param message 発言内容
    */
  def run(message: Message) {
    // bot にメンションされた時だけ動作する
    val mentionedIds = SlackUtil.extractMentionedIds(message.text)
    if (!mentionedIds.contains(botId)) {
      return
    }

    // 操作が許可されたユーザーの発言でのみ動作する
    if (message.user != operatableUserId) {
      return
    }

    // コマンドに応じて処理を行う
    Command.dispatch(message.text) match {
      case Some(TweetCommand) =>
        twitter.tweet(Command.content(message.text)) match {
          case Right(_) =>
          case Left(error_message) => client.sendMessage(message.channel, error_message)
        }
    }
  }
}
