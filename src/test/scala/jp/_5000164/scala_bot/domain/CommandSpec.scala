package jp._5000164.scala_bot.domain

import org.scalatest.FreeSpec

class CommandSpec extends FreeSpec {
  "ttt をツイート用のコマンドだと判断する" in {
    assert(Command.dispatch("<@AAAAAAAAA> ttt text").contains(TweetCommand))
  }

  "aaa はどのコマンドでもないと判断する" in {
    assert(Command.dispatch("<@AAAAAAAAA> aaa text").isEmpty)
  }
}
