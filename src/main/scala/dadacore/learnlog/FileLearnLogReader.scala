package dadacore.learnlog

import java.text.SimpleDateFormat
import java.io.FileNotFoundException

class FileLearnLogReader(filename:String) extends LearnLogReader {
  // TODO: locking
  private val dateFormat = new SimpleDateFormat("yyyy-MM-dd,hh:mm")
  private var inputIterator:Iterator[String] = null
  try {
    inputIterator = io.Source.fromFile(filename, "UTF-8").getLines()
  } catch {
    case e:FileNotFoundException => throw new LearnLogNoSuchLogError(filename)
  }

  private def parseLine(line:String): LearnLogElement =
    line.split(" ", 4).toList match {
      case List(dateString, user, source, text) =>
        val date = dateFormat.parse(dateString)
        new LearnLogElement(date, user, source, text)
      case _ => throw new LearnLogInvalidLineException(line)
    }

  def hasNext = inputIterator.hasNext
  def next() = parseLine(inputIterator.next())
}
