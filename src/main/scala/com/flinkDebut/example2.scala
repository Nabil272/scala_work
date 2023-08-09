package com.flinkDebut

import org.apache.flink.api.scala._
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.parquet.schema.MessageType
import org.apache.parquet.schema.Types
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName
import org.apache.parquet.schema.OriginalType
import org.apache.flink.formats.parquet._
import org.apache.flink.streaming.api.scala._
//import org.apache.hadoop.mapreduce.Job
//import org.apache.flink.api.java.hadoop.mapreduce.HadoopInputFormat
//import org.apache.hadoop.io.ArrayWritable
//import org.apache.flink.hadoopcompatibility.HadoopInputs
import org.apache.parquet.hadoop.ParquetInputFormat
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row
import org.apache.flink.api.java.typeutils.RowTypeInfo
import org.apache.flink.api.common.typeinfo.BasicTypeInfo
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.common.typeinfo.BasicTypeInfo
import org.apache.flink.api.java.typeutils.RowTypeInfo




object flinkExample2{

  def main(args: Array[String]): Unit = {


    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val messageTypeBuilder = Types.buildMessage()

    // Ajout des champs de type StringType
    val stringFields = List(
      "ean", "product_remote_id", "name", "price", "image", "url", "brand",
      "description", "upc", "packaging", "shipping_fees", "discount_remote_id",
      "delivery_delay", "discount", "currency", "stock", "availability"
    )

    stringFields.foreach { field =>
      messageTypeBuilder.required(PrimitiveTypeName.BINARY).as(OriginalType.UTF8).named(field)
    }

    // Ajout des champs de type IntegerType
    val integerFields = List(
      "retail_outlet_remote_id", "retail_outlet_id", "rds_id"
    )

    integerFields.foreach { field =>
      messageTypeBuilder.required(PrimitiveTypeName.INT32).named(field)
    }

    val filePath = "/Users/nabilito/scala_work/data/flow_1346.parquet"


    val tableEnv = StreamTableEnvironment.create(env)
    tableEnv.executeSql(
      s"""
         |CREATE TABLE MyParquetTable (
         |  ean STRING,
         |  product_remote_id STRING,
         |  name STRING,
         |  price STRING,
         |  image STRING,
         |  url STRING,
         |  brand STRING,
         |  description STRING,
         |  upc STRING,
         |  packaging STRING,
         |  retail_outlet_remote_id INT,
         |  retail_outlet_id INT,
         |  rds_id INT,
         |  shipping_fees STRING,
         |  discount_remote_id STRING,
         |  delivery_delay STRING,
         |  discount STRING,
         |  currency STRING,
         |  stock STRING,
         |  availability STRING
         |) WITH (
         |  'connector' = 'filesystem',
         |  'path' = '$filePath',
         |  'format' = 'parquet'
         |)
      """.stripMargin)

    val fieldTypes: Array[TypeInformation[_]] = Array(
      BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
      BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
      BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
      BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO,
      BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
      BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
      BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO
    )
    val rowTypeInfo = new RowTypeInfo(fieldTypes: _*)

    val table: Table = tableEnv.from("MyParquetTable")

    implicit val typeInfo: TypeInformation[Row] = rowTypeInfo
    val dataStream: DataStream[Row] = table.toDataStream


    dataStream.print()


  }

}
