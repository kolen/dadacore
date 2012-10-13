package dadacore.learnlog

import java.io.{FileOutputStream, OutputStreamWriter, BufferedWriter}
import java.nio.charset.Charset
import java.text.SimpleDateFormat

class FileLearnLogWriter(filename: String) extends LearnLogWriter {
  val stream = new FileOutputStream(filename, true)
  val writer = new BufferedWriter(
    new OutputStreamWriter(stream, Charset.forName("UTF-8")))
  val dateFormatter = new SimpleDateFormat("yyyy-MM-dd,hh:mm")

  def write(logElement: LearnLogElement) {
    val modifiedText = logElement.text.replaceAll("\\s+", " ")
    val lock = stream.getChannel.lock()
    try {
      writer.write(
        List(
          dateFormatter.format(logElement.date),
          logElement.user,
          logElement.source,
          modifiedText
        ).mkString("", " ", "\n")
      )
      writer.flush()
    } finally {
      lock.release()
    }
  }
}