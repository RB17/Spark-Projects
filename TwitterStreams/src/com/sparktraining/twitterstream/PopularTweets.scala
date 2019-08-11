package com.sparktraining.twitterstream

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.twitter.TwitterUtils

object PopularTweets {
  def setupLogging() = {
    import org.apache.log4j.{Level, Logger}
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
  }

  def setupTwitter() = {
    import scala.io.Source
    for(line <- Source.fromFile("C:\\Users\\rangan_basu\\Documents\\Spark\\TwitterJars\\twitter.txt").getLines()){
      val fields = line.split(" ")
      if(fields.length == 2){
        System.setProperty("twitter4j.oauth."+fields(0),fields(1))
      }
    }

  }

  def main(args: Array[String]): Unit = {
    setupTwitter()

    val ssc = new StreamingContext("local[*]", "PopularTweets", Seconds(1))

    setupLogging()

    val tweets = TwitterUtils.createStream(ssc,None)
    val statuses = tweets.map(status => status.getText())
    val tweetwords = statuses.flatMap(tweetText=>tweetText.split(" "))
    val hashtags =  tweetwords.filter(word=>word.startsWith("#"))
    val hashtagKeyVal = hashtags.map(hashtag=>(hashtag,1))
    val hashtagCount = hashtagKeyVal.reduceByKeyAndWindow((x,y)=>x+y,(x,y)=>x-y, Seconds(300),Seconds(1))
    val sortedResutd = hashtagCount.transform(rdd=>rdd.sortBy(x=>x._2,false))
    sortedResutd.print
    ssc.checkpoint(("C:/checkpoint/"))
    ssc.start()
    ssc.awaitTermination()
  }
}
