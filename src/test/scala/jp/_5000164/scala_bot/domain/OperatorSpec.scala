package jp._5000164.scala_bot.domain

import jp._5000164.scala_bot.interfaces.Twitter
import org.mockito.Mockito.verify
import org.scalatest.FreeSpec
import org.scalatest.mockito.MockitoSugar
import slack.models.Message

class OperatorSpec extends FreeSpec with MockitoSugar {
  "つぶやく内容だけを渡す" in {
    val mockTwitter = mock[Twitter]
    val operator = new Operator(mockTwitter, "AAAAAAAAA", "BBBBBBBBB")
    val message = new Message(
      "ts",
      "channel",
      "BBBBBBBBB",
      "<@AAAAAAAAA> ttt text",
      None,
      None
    )
    operator.operate(message)

    verify(mockTwitter).tweet("text")
  }
}
