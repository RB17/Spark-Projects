package com.ScalaSimpliLearn.org

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._

object DataFrames {
  case class Person(ID: Int, name: String, age: Int, numFriends: Int)

  def mapper(line: String): Person = {
    val fields = line.split(",")
    val person: Person = Person(fields(0).toInt, fields(1), fields(2).toInt, fields(3).toInt)
    return person
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder()
      .appName("DataFrames")
      .master("local[*]")
      .config("spark.sql.warehouse.dir","file:///C:/temp")
      .getOrCreate()

    import spark.implicits._
    val lines = spark.sparkContext.textFile("C:\\Users\\rangan_basu\\Documents\\Spark\\workspace\\SparkUdemy\\src\\com\\sparkscalarb\\fakefriends.csv")
    val people = lines.map(mapper).toDS().cache()

    println("Schema Details")
    people.printSchema()
    
    println("Select Name coulmn")
    people.select("name").show()

    println("Filter < 21")
    people.filter(people("age") < 21).show()

    println("GroupBy Age")
    people.groupBy("age").count().show()

    println("Make all 10 years older")
    people.select(people("name"), people("age")+10).show()

    spark.stop()
  }
}
