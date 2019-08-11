package com.ScalaSimpliLearn.org

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._


object RatingsCounter {

  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]","RatingsCounter")

    val lines = sc.textFile("C:\\Users\\rangan_basu\\Documents\\Spark\\SimplilearnTrainingData\\u.data")

    val ratings = lines.map(x=> x.toString().split('\t')(2))

    val results = ratings.countByValue()

    val sortedData = results.toSeq.sortBy(_._1)
    println(results)
    sortedData.foreach(println)
  }

}
