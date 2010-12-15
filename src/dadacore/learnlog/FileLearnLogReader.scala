package dadacore.learnlog

import java.io.{FileInputStream, InputStreamReader, BufferedReader}
import annotation.tailrec
import java.util.Date
import java.text.{SimpleDateFormat}

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

// java.lang.AssertionError: expected:
// <List(LearnLogElement Mon Jan 16 10:56:00 MSK 3911 mithgol wikipedia,2ch Понятие шушпанчика зародилось в сети Фидонет в 2001 году, его происхождение связано с именем одного из пойнтов этой сети, LearnLogElement Tue Jan 17 11:23:00 MSK 3911 ziggol goatse.cx Сын, почему от тебя пахнет зигами? - Ты опять на Манежную площадь ходил? - Не, мам, просто у метро назиговано было!)>
// <List(LearnLogElement Thu Dec 16 10:56:00 MSK 2010 mithgol wikipedia,2-ch Понятие шушпанчика зародилось в сети Фидонет в 2001 году, его происхождение связано с именем одного из пойнтов этой сети, LearnLogElement Fri Dec 17 11:23:00 MSK 2010 ziggol goatse.cx Сын, почему от тебя пахнет зигами? - Ты опять на Манежную площадь ходил? - Не, мам, просто у метро назиговано было!)>

//java.lang.AssertionError: expected:
// <List(LearnLogElement Mon Jan 16 10:56:00 MSK 3911 mithgol wikipedia,2-ch Понятие шушпанчика зародилось в сети Фидонет в 2001 году, его происхождение связано с именем одного из пойнтов этой сети, LearnLogElement Tue Jan 17 11:23:00 MSK 3911 ziggol goatse.cx Сын, почему от тебя пахнет зигами? - Ты опять на Манежную площадь ходил? - Не, мам, просто у метро назиговано было!)> but was:
// <List(LearnLogElement Thu Dec 16 10:56:00 MSK 2010 mithgol wikipedia,2-ch Понятие шушпанчика зародилось в сети Фидонет в 2001 году, его происхождение связано с именем одного из пойнтов этой сети, LearnLogElement Fri Dec 17 11:23:00 MSK 2010 ziggol goatse.cx Сын, почему от тебя пахнет зигами? - Ты опять на Манежную площадь ходил? - Не, мам, просто у метро назиговано было!)>