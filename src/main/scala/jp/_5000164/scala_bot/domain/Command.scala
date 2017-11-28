package jp._5000164.scala_bot.domain

sealed abstract class CommandBase
case object TweetCommand extends CommandBase

/**
  * コマンドを振り分ける
  */
object Command {
  /**
    * コマンドを決定する
    *
    * @param text 発言した内容
    * @return どのコマンドを実行するか
    */
  def dispatch(text: String): Option[CommandBase] = {
    text.substring(13).take(3) match {
      case "ttt" => Some(TweetCommand)
      case _ => None
    }
  }

  /**
    * コマンドに渡す内容を取得する
    *
    * @param text 発言した内容
    * @return コマンドに渡す内容
    */
  def content(text: String): String = {
    text.substring(17)
  }
}
