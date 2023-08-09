# Flink installation 

## 1) brew install apache-flink 

## 2) go to /usr/local/Cellar/apache-flink/version/libexec/bin and start the cluster and the task manager with : ./start-cluster.sh and taskmanager.sh start 

## 3) build a fat jar of your flink application in scala (sbt) 
add this to your build.sbt :

assemblyMergeStrategy in assembly := {
case PathList("META-INF", xs @ _*) => MergeStrategy.discard
case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
case x if x.endsWith("module-info.class") => MergeStrategy.first
case x => (assemblyMergeStrategy in assembly).value(x)
}

and add also the mainclass of your flink script : 

mainClass in assembly := Some("com.flinkDebut.wordCount")

## 4) add the sbt assembly plugin 
create a file called plugins.sbt in project folder of your sbt application and paste this

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.15.0")


## 5) check your cluster ui on localhost:8081 


## 6) generate your jar with sbt assembly , the jar will be located in target/scala-version/

## 7) run flink with the path of your jar  

flink run target/scala-2.12/scala_work-assembly-0.1.0-SNAPSHOT.jar --input /Users/nabilito/scala_work/data/input.txt --output /Users/nabilito/scala_work/data/output.txt

