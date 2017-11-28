package jp._5000164.scala_bot.domain

sealed abstract class Command
case object Tweet extends Command

/**
  * コマンドを振り分ける
  */
object CommandDispatcher {
  /**
    * コマンドを決定する
    *
    * @param text 発言した内容
    * @return どのコマンドを実行するか
    */
  def decide(text: String): Option[Command] = {
    text.substring(13).take(3) match {
      case "ttt" => Some(Tweet)
      case _ => None
    }
  }
}
