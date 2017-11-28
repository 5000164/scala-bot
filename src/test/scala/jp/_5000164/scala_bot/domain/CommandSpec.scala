package jp._5000164.scala_bot.domain

import org.scalatest.FreeSpec

class CommandSpec extends FreeSpec {
  "ttt をツイート用のコマンドだと判断する" in {
    assert(Command.dispatch("<@AAAAAAAAA> ttt text").contains(TweetCommand))
  }

  "aaa はどのコマンドでもないと判断する" in {
    assert(Command.dispatch("<@AAAAAAAAA> aaa text").isEmpty)
  }

  "コマンドの後に続く文字列をコマンドに渡す内容として取得する" in {
    assert(Command.content("<@AAAAAAAAA> ttt text") == "text")
  }
}
