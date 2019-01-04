import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import scala.util.matching.Regex
import scala.collection.mutable.ArrayBuffer
import scala.util.Try
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import java.io.File
import java.io.PrintWriter
import scala.io.Source
import models.dataframe._
import com.github.nscala_time.time.Imports._
import org.joda.time.{Days,Months}

object Main extends App {
  
  /* case class log(AD: SimpleDateFormat("y-M-d H:m:s"), team: String, leader: String, ED: SimpleDateFormat("y-M-d"), DD: SimpleDateFormat("y-M-d"),trail: String, members: Int, bed_camp: Int, state: String) */
  val pattern = new Regex("[0-9]+")
  val browser = JsoupBrowser()
  var option_code = ArrayBuffer[String]()
  val format = new SimpleDateFormat("y-M-d")
  
  val writer = new PrintWriter(new File("Write.txt"))
  var counter = 1
  for {
    test <- browser.get("https://npm.cpami.gov.tw/en/bed_1main.aspx?orgid=e6dd4652-2d37-4346-8f5d-6e538353e0c2&node_id=13&sdate=2019-01-11") >?> element("tbody") 
    line <- browser.get("https://npm.cpami.gov.tw/en/bed_1main.aspx?orgid=e6dd4652-2d37-4346-8f5d-6e538353e0c2&node_id=13&sdate=2019-01-11") >> "tbody tr"  
      
     
  } /* try writer.write(line.text+"\n ") */
  
  writer.close()
  for (line <- Source.fromFile("Write.txt").getLines) {
      println(line.toString)
  
}
  val log_data: DataFrame = DataFrame("Write.txt")
  
  for {
    id <- browser.get("https://npm.cpami.gov.tw/en/bed_1.aspx") >> "option"    
  }  try option_code += (pattern findFirstIn id.toString).toString.replaceAll("[^\\d.]", "") /* .mkString(",") */

  println(format.format(Calendar.getInstance().getTime()))
  val DATE_PATTERN = DateTimeFormat.forPattern("yyyy-MM-dd")
  val now = DateTime.now
/*  (7 until 30).map(now.plusDays(_)).foreach(println) */
  
/*  for(camp_code <- option_code) println(camp_code) */

  val start = DateTime.now + 7.days
  val end   = DateTime.now + 1.months    

  val daysCount = Days.daysBetween(start, end).getDays()
/*  (0 until daysCount).map(start.plusDays(_)).foreach(println) */
  
      
  
/*  val lines = Source.fromFile("Write.txt").getLines.toList
*  println(lines)
*  option_code.foreach(println)
*  println(option_code.mkString(" "))    

* val pattern = new Regex("[0-9]+")
* val str = "ablaw is =56>able1 and cool"
      
* println((pattern findAllIn str).mkString(","))  
*/   
}
