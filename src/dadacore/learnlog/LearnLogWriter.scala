package dadacore.learnlog

import java.util.Date

trait LearnLogWriter {
  def write(user:String, source:String, text:String)
  def write(date:Date, user:String, source:String, text:String)
  def write(log_element:LearnLogElement)
}
