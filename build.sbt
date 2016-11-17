name := "scala-sbt"

version := "1.0"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.apache.hadoop" % "hadoop-hdfs" % "2.7.3",
  "org.apache.kafka" % "kafka_2.11" % "0.10.1.0"
)
