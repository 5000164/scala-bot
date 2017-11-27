package jp._5000164.scala_bot.interfaces

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
    // bot にメンションされた時だけ反応するためにメンション先をすべて取得する
    val mentionedIds = SlackUtil.extractMentionedIds(message.text)

    if (mentionedIds.contains(botId)) {
      if (message.user == operatableUserId) {
        if (message.text.substring(13).take(3) == "ttt") {
          try {
            twitter.tweet(message.text.substring(17))
          } catch {
            case _: Exception => client.sendMessage(message.channel, "ツイート送信失敗")
          }
        }
      }
    }
  }
}
