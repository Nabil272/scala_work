package com.flinkDebut

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.connector.kafka.sink.{KafkaRecordSerializationSchema, KafkaSink}
import org.apache.flink.streaming.api.scala._


object flinkExample1 {


  def main(args:Array[String]): Unit = {
    // instancie l'env d'execution flink
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // kafka server
    val kafkaSource = KafkaSource.builder()
      .setBootstrapServers("localhost:29092")
      .setTopics("bor_prices_topic")
      .setGroupId("flink-consumer-group")
      .setStartingOffsets(OffsetsInitializer.latest())
      .setValueOnlyDeserializer(new SimpleStringSchema())
      .build()


    // avant d'envoyer vers kafka , on doit sérializer la donnée
    val serializer = KafkaRecordSerializationSchema.builder()
      .setValueSerializationSchema(new SimpleStringSchema())
      .setTopic("flink-example-out")
      .build()

    // kafka sink
    val kafkaSink = KafkaSink.builder()
      .setBootstrapServers("localhost:29092")
      .setRecordSerializer(serializer)
      .build()


    val lines : DataStream[String] = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source")

    // faire un map sur le message en rajoutant un string
    val transformed = lines.map(line => "Transformed: " + line)


    transformed.sinkTo(kafkaSink)

    env.execute("Read from Kafka")

  }
}
