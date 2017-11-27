package jp._5000164.scala_bot.interfaces

import org.mockito.Mockito.verify
import org.scalatest.FreeSpec
import org.scalatest.mockito.MockitoSugar
import slack.models.Message
import slack.rtm.SlackRtmClient

class OperatorSpec extends FreeSpec with MockitoSugar {
  "つぶやく内容だけを渡す" in {
    val mockClient = mock[SlackRtmClient]
    val mockTwitter = mock[Twitter]
    val operator = new Operator(mockClient, "AAAAAAAAA", "BBBBBBBBB", mockTwitter)
    val message = new Message(
      "ts",
      "channel",
      "BBBBBBBBB",
      "<@AAAAAAAAA> ttt text",
      None,
      None
    )
    operator.run(message)

    verify(mockTwitter).tweet("text")
  }
}
