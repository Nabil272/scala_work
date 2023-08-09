package com.flinkDebut

import org.apache.flink.api.scala._
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.common.functions.FlatMapFunction


/* WORD COUNT ==>
le but de ce script est d'appliquer un word count sur flink
 */


object wordCount {

  def main(args: Array[String]): Unit = {

    // ParameterTool
    val params: ParameterTool = ParameterTool.fromArgs(args)

    // set up the streaming execution environment
    val env = ExecutionEnvironment.getExecutionEnvironment

    // make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val text = env.readTextFile(params.get("input"))

    val counts = text
      .flatMap(value => value.split("\\s+"))
      .map(value => (value, 1))
      .groupBy(0)
      .sum(1)

    counts.writeAsCsv(params.get("output"), "\n", " ", org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE)

    env.execute("Scala WordCount Example")

  }
}
