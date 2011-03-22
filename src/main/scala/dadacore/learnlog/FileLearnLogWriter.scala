package dadacore.learnlog

import java.io.{FileOutputStream, OutputStreamWriter, BufferedWriter}
import java.nio.charset.Charset
import java.text.SimpleDateFormat

class FileLearnLogWriter(filename: String) extends LearnLogWriter {
  val writer = new BufferedWriter(
    new OutputStreamWriter(new FileOutputStream(filename, true),
    Charset.forName("UTF-8")))
  val date_formatter = new SimpleDateFormat("yyyy-MM-dd,hh:mm")

  def write(log_element: LearnLogElement) {
    val modified_text = log_element.text.replaceAll("\\s+", " ")
    writer.write(
      List(
        date_formatter.format(log_element.date),
        log_element.user,
        log_element.source,
        modified_text
      ).mkString("", " ", "\n")
    )
    writer.flush
  }
}