package dadacore.learnlog

import java.util.Date

case class LearnLogElement(date:Date, user:String, source:String, text:String) {
  override def toString = "LearnLogElement " + date.toString + " " + user + " " + source + " " + text
}