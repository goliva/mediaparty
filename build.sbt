name := "MediaPartyFE"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "redis.clients" % "jedis" % "2.6.1",
  "org.json" % "json" % "20160810"
)     

play.Project.playJavaSettings
