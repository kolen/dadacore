package dadacore.test.learnlog

import java.io.{FileOutputStream, OutputStreamWriter, BufferedWriter, File}
import java.nio.charset.Charset
import dadacore.learnlog.{LearnLogElement, FileLearnLogReader}
import java.text.SimpleDateFormat
import org.specs2.mutable._

object FileLearnLogReaderSpec extends Specification {
  "FileLearnLogReader" should {
    "Read example file" in {
      val tempfile = File.createTempFile("learn", "log")
      tempfile.deleteOnExit()

      val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempfile), Charset.forName("UTF-8")))
      writer write "2010-12-16,10:56 mithgol wikipedia,2-ch Понятие шушпанчика зародилось в сети Фидонет в 2001 году, его происхождение связано с именем одного из пойнтов этой сети"
      writer newLine()
      writer write "2010-12-17,11:23 ziggol goatse.cx Сын, почему от тебя пахнет зигами? - Ты опять на Манежную площадь ходил? - Не, мам, просто у метро назиговано было!"
      writer newLine()
      writer close()

      val log_reader = new FileLearnLogReader(tempfile.getAbsolutePath)
      val elements = log_reader.toList
      val formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm")

      elements must_== List(
        new LearnLogElement(formatter.parse("2010/12/16 10:56"), "mithgol", "wikipedia,2-ch",
          "Понятие шушпанчика зародилось в сети Фидонет в 2001 году, его происхождение связано с именем одного из пойнтов этой сети"
        ),
        new LearnLogElement(formatter.parse("2010/12/17 11:23"), "ziggol", "goatse.cx",
          "Сын, почему от тебя пахнет зигами? - Ты опять на Манежную площадь ходил? - Не, мам, просто у метро назиговано было!"
        )
      )
    }
  }
}