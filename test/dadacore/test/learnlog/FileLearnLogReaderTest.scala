package dadacore.test.learnlog

import org.junit.{After, Before, Test, Assert}
import Assert._
import java.io.{FileOutputStream, OutputStreamWriter, BufferedWriter, File}
import java.nio.charset.Charset
import dadacore.learnlog.{LearnLogElement, FileLearnLogReader}
import java.util.Date

class FileLearnLogReaderTest {
  @Test
  def test {
    val tempfile = File.createTempFile("learn", "log")
    tempfile.deleteOnExit

    val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempfile), Charset.forName("UTF-8")))
    writer write "2010-12-16,10:56 mithgol wikipedia,2-ch Понятие шушпанчика зародилось в сети Фидонет в 2001 году, его происхождение связано с именем одного из пойнтов этой сети"
    writer newLine()
    writer write "2010-12-17,11:23 ziggol goatse.cx Сын, почему от тебя пахнет зигами? - Ты опять на Манежную площадь ходил? - Не, мам, просто у метро назиговано было!"
    writer newLine()
    writer close()

    val logreader = new FileLearnLogReader(tempfile.getAbsolutePath)
    val elements = logreader.toList
    assertEquals(
      List(
        new LearnLogElement(new Date(2010, 12, 16, 10, 56), "mithgol", "wikipedia,2ch",
          "Понятие шушпанчика зародилось в сети Фидонет в 2001 году, его происхождение связано с именем одного из пойнтов этой сети"
        ),
        new LearnLogElement(new Date(2010, 12, 17, 11, 23), "ziggol", "goatse.cx",
          "Сын, почему от тебя пахнет зигами? - Ты опять на Манежную площадь ходил? - Не, мам, просто у метро назиговано было!"
        )
      ),
      elements
    )
  }
}