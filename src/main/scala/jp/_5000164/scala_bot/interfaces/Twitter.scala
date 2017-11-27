package jp._5000164.scala_bot.interfaces

import twitter4j.TwitterFactory

/**
  * Twitter とやりとりをする
  */
class Twitter {
  /**
    * ツイートする
    *
    * @param text ツイートする内容
    * @return ツイートに成功したかどうか
    */
  def tweet(text: String): Boolean = {
    try {
      new TwitterFactory().getInstance().updateStatus(text)
      true
    } catch {
      case e: Exception => false
    }
  }
}
