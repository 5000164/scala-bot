name := "scala-bot"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
libraryDependencies += "org.mockito" % "mockito-core" % "2.12.0" % "test"

libraryDependencies += "com.github.gilbertw1" %% "slack-scala-client" % "0.2.2"
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.6"
