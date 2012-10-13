package dadacore.learnlog

import java.text.SimpleDateFormat
import java.io.FileNotFoundException

class FileLearnLogReader(filename:String) extends LearnLogReader {
  // TODO: locking
  private val date_format = new SimpleDateFormat("yyyy-MM-dd,hh:mm")
  private var input_iterator:Iterator[String] = null
  try {
    input_iterator = io.Source.fromFile(filename, "UTF-8").getLines()
  } catch {
    case e:FileNotFoundException => throw new LearnLogNoSuchLogError(filename)
  }

  private def parseLine(line:String): LearnLogElement =
    line.split(" ", 4).toList match {
      case List(dateString, user, source, text) =>
        val date = date_format.parse(dateString)
        new LearnLogElement(date, user, source, text)
      case _ => throw new LearnLogInvalidLineException(line)
    }

  def hasNext = input_iterator.hasNext
  def next() = parseLine(input_iterator.next())
}
