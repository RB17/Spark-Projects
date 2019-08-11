package com.realTimeProjectRb.org

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import java.nio.charset.CodingErrorAction
import scala.io.Codec

object analytics {
  
  def main(args: Array[String]) {
    
  Logger.getLogger("org").setLevel(Level.ERROR)
  
  // Handle character encoding issues:
  implicit val codec = Codec("UTF-8")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)
    
  val sc = new SparkContext("local[*]", "analytics")   
  
  // load data and create spark data frame 
  val input = sc.textFile("C:\\Users\\rangan_basu\\Documents\\Spark\\workspace\\MarketingAnalysis\\bank-full.csv").map(x => x.split(";(?=([^\"]*\"[^\"]*\")*[^\"]*$)",-1))
  
  val header = input.first()
  val filtered = input.filter(x => x(0)!= header(0))
  val rdds = filtered.map(x => Row(x(0).toInt, x(1),x(2),x(3),x(4), x(5).toInt,x(6),x(7),x(8), x(9).toInt,x(10),x(11).toInt,x(12).toInt, x(13).toInt,x(14).toInt,x(15),x(16) ))
  
  val schema = StructType( List(StructField("age", IntegerType, true),
      StructField("job", StringType, true) ,
      StructField("marital", StringType, true),
      StructField("education", StringType, true) ,
      StructField("default", StringType, true),
      StructField("balance", IntegerType, true) ,
      StructField("housing", StringType, true) 	,
      StructField("loan", StringType, true) ,
      StructField("contact", StringType, true) ,
      StructField("day", IntegerType, true) ,
      StructField("month", StringType, true) ,
      StructField("duration", IntegerType, true) ,
      StructField("campaign", IntegerType, true) ,
      StructField("pdays", IntegerType, true) ,
      StructField("previous", IntegerType, true) ,
      StructField("poutcome", StringType, true) ,
      StructField("y", StringType, true)) )
    
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._  
    val df = sqlContext.createDataFrame(rdds, schema)
    
    
    df.printSchema()
    
    // Give marketing success rate. (No. of people subscribed / total no. of entries)
    val successRate = ( (df.filter(df("y")==="\"yes\"").count().toDouble) / (df.count().toDouble) ) * 100
    
    // Check max, min, Mean and median age of average targeted customer 
    import org.apache.spark.sql.functions.{min, max, mean, count, when}
    df.agg(max(df("age")), min(df("age")) , mean(df("age"))).show()
    
    // Check quality of clients by checking average balance, median balance of clients
    df.agg(mean(df("balance"))).show()
    println(df.stat.approxQuantile("balance", Array(0.5), 0)(0))
    
    // Check if age matters in marketing subscription for deposit - Not Really !!
    df.groupBy("y").agg(mean(df("age"))).show()
    
    // Check if marital status mattered for subscription to deposit. - Not Really
    df.groupBy("y").agg(count(df("marital"))).show()
    
    // Check if age and marital status together mattered for subscription to deposit scheme 
    df.groupBy("marital","y").count.show()
    df.groupBy("age","y").count.show()
    
    // Do Feature engineering for age column and find right age effect on campaign
    val df_new = df.withColumn("age_cat", when($"age" < 25 , "young").otherwise( when($"age" > 60 , "old").otherwise("mid_age")  ))

    df_new.groupBy("age_cat","y").count.show()

  }
}