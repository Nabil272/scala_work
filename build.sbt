import sbt._

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.18"

val flinkVersion = "1.17.1"
val kafkaVersion = "3.2.0"

lazy val flinkDependencies: Seq[sbt.librarymanagement.ModuleID] = {
  def flinkModule(name: String) = "org.apache.flink" % s"flink-$name" % flinkVersion

  Seq(
    "java",
    "core",
    "connector-kafka",
    "avro",
    "clients",
    "connector-files",
    "statebackend-rocksdb",
    "parquet"
  ).map(flinkModule) :+
    "org.apache.flink" %% "flink-streaming-scala" % flinkVersion
}

lazy val kafkaDependencies: Seq[sbt.librarymanagement.ModuleID] = Seq(
  "org.apache.kafka" % "kafka-clients" % kafkaVersion   // Ajoutez la dépendance Kafka ici
)

lazy val root = (project in file("."))
  .settings(
    name := "scala_work",
    libraryDependencies ++= (flinkDependencies ++ kafkaDependencies)
  )

//mainClass in assembly := Some("com.flinkDebut.wordCount")
//mainClass in assembly := Some("com.flinkDebut.flinkExample1")
mainClass in assembly := Some("com.flinkDebut.flinkExample2")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case x if x.endsWith("module-info.class") => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}


// ajouter les logs logback
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"


libraryDependencies += "org.apache.flink" %% "flink-table-api-scala-bridge" % flinkVersion
libraryDependencies += "org.apache.flink" % "flink-connector-base" % flinkVersion



libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % "provided",
  //"org.apache.hadoop" % "hadoop-common" % "3.3.4",
  //"org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.3.4",



  //"com.github.mjakubowski84" %% "parquet4s-core" % "1.0.0",
  //"org.apache.hadoop" % "hadoop-client" % "2.4.0",
  //"org.apache.flink" %% "flink-hadoop-compatibility" % flinkVersion % "provided",
  //"org.apache.parquet" % "parquet-hadoop" % "1.11.1",
  "org.apache.flink" % "flink-table-common" % flinkVersion

)
















