package com.sparkscalarb.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._

object SparkSQL {
  
  case class Person(ID:Int, name:String, age:Int, numFriends:Int)
  
  def mapper(line:String): Person = {
    val fields = line.split(',')  
    
    val person:Person = Person(fields(0).toInt, fields(1), fields(2).toInt, fields(3).toInt)
    
    person
  }
  
  /** Our main function where the action happens */
  def main(args: Array[String]) {
    
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    
    // Use new SparkSession interface in Spark 2.0
    
    // Spark Session wraps both a SparkContext and SQL Context Within it  
    val spark = SparkSession
      .builder
      .appName("SparkSQL")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
      .getOrCreate()
    
    val lines = spark.sparkContext.textFile("C:\\Users\\rangan_basu\\Documents\\Spark\\workspace\\SparkUdemy\\src\\com\\sparkscalarb\\fakefriends.csv")
    val people = lines.map(mapper)
    
    // Infer the schema, and register the DataSet as a table.
    // Import implicits so that Spark can convert the structured RDD into a Data Set
    import spark.implicits._
    val schemaPeople = people.toDS
    
    schemaPeople.printSchema()
    
    // Convert Contents of the Data Set into what looks like a SQL Table with the name 'People'
    schemaPeople.createOrReplaceTempView("people")
    
    // SQL can be run over DataFrames that have been registered as a table
    val teenagers = spark.sql("SELECT * FROM people WHERE age >= 13 AND age <= 19 AND numFriends>250")
    
    val results = teenagers.collect()
    
    println(results.length)
    
    results.foreach(println)
    
    // Stop the SPark Session when done
    spark.stop()
  }
}