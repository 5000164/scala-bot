package domain

import infractructure.Twitter
import slack.SlackUtil
import slack.models.Message

/**
 * 処理を行う
 *
 * @param twitter          Twitter に関する処理を行うため
 * @param botId            自身の発言を区別するため
 * @param operatableUserId 特定のユーザーだけが処理を行えるように保持する
 */
class Operator(val twitter: Twitter, val botId: String, val operatableUserId: String) {
  /**
   * メッセージに応じた処理を行う
   *
   * @param message 発言内容
   */
  def operate(message: Message): Option[String] = {
    val mentionedIds = SlackUtil.extractMentionedIds(message.text)

    if (message.user == botId) {
      None
    } else if (mentionedIds.contains(botId)) {
      if (message.user == operatableUserId) {
        if (message.text.substring(13).take(3) == "ttt") {
          try {
            twitter.tweet(message.text.substring(17))
            None
          } catch {
            case e: Exception => Some("ツイート送信失敗")
          }
        } else {
          None
        }
      } else {
        None
      }
    } else {
      None
    }
  }
}
