package dadacore.learnlog

import java.io.{FileInputStream, InputStreamReader, BufferedReader}
import annotation.tailrec
import java.util.Date

class FileLearnLogReader(filename:String) extends LearnLogReader {
  private var br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))


  def foreach[U](f: (LearnLogElement) => U) = {
    def parseLine(line:String): LearnLogElement =
      line.split(" ", 4).toList match {
        case List(datestr, user, source, text) =>
          val date = new Date(datestr)
          new LearnLogElement(date, user, source, text)
        case _ => throw new LearnLogInvalidLineException(line)
      }

    @tailrec
    def readLine:Unit = br.readLine match {
      case line:String =>
        f(parseLine(line))
        readLine
      case null => Nil
    }
  }


}