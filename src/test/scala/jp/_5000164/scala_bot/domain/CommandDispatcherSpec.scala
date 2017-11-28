package jp._5000164.scala_bot.domain

import org.scalatest.FreeSpec

class CommandDispatcherSpec extends FreeSpec {
  "ttt をツイート用のコマンドだと判断する" in {
    assert(CommandDispatcher.decide("<@AAAAAAAAA> ttt text").contains(Tweet))
  }

  "aaa はどのコマンドでもないと判断する" in {
    assert(CommandDispatcher.decide("<@AAAAAAAAA> aaa text").isEmpty)
  }
}
