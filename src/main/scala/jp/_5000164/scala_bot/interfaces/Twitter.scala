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
    * @return ツイートに失敗した場合はエラーメッセージを返す
    */
  def tweet(text: String): Either[String, Unit] = {
    try {
      new TwitterFactory().getInstance().updateStatus(text)
      Right(Unit)
    } catch {
      case e: Exception => Left(e.toString)
    }
  }
}
