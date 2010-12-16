package dadacore.learnlog

import java.text.SimpleDateFormat

class FileLearnLogReader(filename:String) extends LearnLogReader {
  private val date_format = new SimpleDateFormat("yyyy-MM-dd,hh:mm")
  private val input_iterator = io.Source.fromFile(filename, "UTF-8").getLines()

  private def parseLine(line:String): LearnLogElement =
    line.split(" ", 4).toList match {
      case List(datestr, user, source, text) =>
        val date = date_format.parse(datestr)
        new LearnLogElement(date, user, source, text)
      case _ => throw new LearnLogInvalidLineException(line)
    }

  def hasNext = input_iterator.hasNext
  def next() = parseLine(input_iterator.next)
}
