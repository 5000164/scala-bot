import akka.actor.ActorSystem
import slack.SlackUtil
import slack.rtm.SlackRtmClient
import twitter4j.TwitterFactory
import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  val token = sys.env("SLACK_TOKEN")
  val operatableUserId = sys.env("OPERATABLE_USER_ID")

  implicit val system: ActorSystem = ActorSystem("slack")
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val client = SlackRtmClient(token)
  val selfId = client.state.self.id

  client.onMessage { message =>
    val mentionedIds = SlackUtil.extractMentionedIds(message.text)

    if (selfId == message.user) {
      // do not reaction
    } else if (mentionedIds.contains(selfId)) {
      if (message.user == operatableUserId) {
        try {
          new TwitterFactory().getInstance().updateStatus(message.text.substring(13))
        } catch {
          case e: Exception => client.sendMessage(message.channel, "ツイート送信失敗")
        }
      }
    }
  }
}
