package jp._5000164.scala_bot.interfaces

import org.mockito.Mockito.{never, verify, when}
import org.scalatest.FreeSpec
import org.scalatest.mockito.MockitoSugar
import slack.models.Message
import slack.rtm.SlackRtmClient

class OperatorSpec extends FreeSpec with MockitoSugar {
  "つぶやく内容だけを渡す" in {
    val mockClient = mock[SlackRtmClient]
    val mockTwitter = mock[Twitter]
    // Right(Unit) とするとなぜかコンパイルが通らないので Right() としている
    when(mockTwitter.tweet("text")).thenReturn(Right())
    val operator = new Operator(mockClient, "AAAAAAAAA", "BBBBBBBBB", mockTwitter)
    val message = Message(
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

  "bot にメンションされていない時は反応しない" in {
    val mockClient = mock[SlackRtmClient]
    val mockTwitter = mock[Twitter]
    val operator = new Operator(mockClient, "AAAAAAAAA", "BBBBBBBBB", mockTwitter)
    val message = Message(
      "ts",
      "channel",
      "BBBBBBBBB",
      "ttt text",
      None,
      None
    )
    operator.run(message)

    verify(mockTwitter, never).tweet("text")
  }

  "操作が許可されたユーザーではない時は反応しない" in {
    val mockClient = mock[SlackRtmClient]
    val mockTwitter = mock[Twitter]
    val operator = new Operator(mockClient, "AAAAAAAAA", "BBBBBBBBB", mockTwitter)
    val message = Message(
      "ts",
      "channel",
      "CCCCCCCCC",
      "<@AAAAAAAAA> ttt text",
      None,
      None
    )
    operator.run(message)

    verify(mockTwitter, never).tweet("text")
  }

  "ツイート処理が例外を投げたら Slack にメッセージを投げる" in {
    val mockClient = mock[SlackRtmClient]
    val mockTwitter = mock[Twitter]
    when(mockTwitter.tweet("text")).thenReturn(Left("message"))
    val operator = new Operator(mockClient, "AAAAAAAAA", "BBBBBBBBB", mockTwitter)
    val message = Message(
      "ts",
      "channel",
      "BBBBBBBBB",
      "<@AAAAAAAAA> ttt text",
      None,
      None
    )
    operator.run(message)

    // なぜか引数の検証ができない
    verify(mockClient).sendMessage("", "")
  }
}
