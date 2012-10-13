package dadacore.learnlog

import java.util.Date

trait LearnLogWriter {
  def write(user:String, source:String, text:String) {
    write(new Date(), user, source, text)
  }
  def write(date:Date, user:String, source:String, text:String) {
    write(new LearnLogElement(date, user, source, text))
  }
  def write(logElement:LearnLogElement)
}
