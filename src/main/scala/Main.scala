import akka.actor.ActorSystem
import slack.SlackUtil
import slack.rtm.SlackRtmClient

object Main extends App {
  val token = sys.env("SLACK_TOKEN")
  implicit val system = ActorSystem("slack")
  implicit val ec = system.dispatcher

  val client = SlackRtmClient(token)
  val selfId = client.state.self.id

  client.onMessage { message =>
    val mentionedIds = SlackUtil.extractMentionedIds(message.text)

    if (mentionedIds.contains(selfId)) {
      client.sendMessage(message.channel, s"<@${message.user}>: Hey!")
    }
  }
}
