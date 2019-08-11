package com.ScalaSimpliLearn.org

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._


object wordcount {
  
  def main(args: Array[String]) {
    
    //Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]","WordCount")
    
    //val input = sc.textFile("C:\\Users\\rangan_basu\\Documents\\Spark\\SimplilearnTrainingData\\testbook.txt")

    val input = sc.textFile("C:\\Users\\rangan_basu\\Downloads\\sample.txt")
    
    val words = input.flatMap( x => x.split(" "))
    
    val wordCount =  words.countByValue();

    wordCount.foreach(println);
    
  }
  
  
}