package dadacore.learnlog

import java.util.Date

case class LearnLogElement(date:Date, user:String, source:String, text:String) {
//  override def equals(obj: AnyRef) =
//    obj match {
//      case that:LearnLogElement =>
//        date == that.date &&
//        user == that.user &&
//        source == that.source &&
//        text == that.text
//      case _ =>
//        false
//    }

  override def toString = "LearnLogElement " + date.toString + " " + user + " " + source + " " + text
}