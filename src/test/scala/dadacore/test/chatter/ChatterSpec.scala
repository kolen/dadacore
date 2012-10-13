package dadacore.test.chatter

import org.specs2.mutable.Specification
import org.specs2.mock._
import dadacore.learnlog._
import java.util.Date
import dadacore.learnsource.{LearnSentence, LearnSourceStore}
import dadacore.chatter.Chatter
import dadacore.model._
import dadacore.text.{Joiner, Splitter}
import dadacore.learnlog.LearnLogElement

object ChatterSpec extends Specification with Mockito {
  class MyLearnSentence extends LearnSentence

  class TestLearnSourceStore extends LearnSourceStore {
    def create() = new MyLearnSentence
  }

  val inputLogSize = 4

  class TestLearnLogReader extends LearnLogReader {
    val l = List.fill(inputLogSize)(LearnLogElement(new Date(1350142369), "user", "sosource", "Жмур повесился в гроб, похрипев.")).iterator

    def hasNext = l.hasNext
    def next() = l.next()
  }

  "Chatter" should {
    val testLearnSourceStore:LearnSourceStore = mock[LearnSourceStore].create() returns new MyLearnSentence

    val testLogWriter = mock[LearnLogWriter]
    val testLogManager = mock[LearnLogManager]
    testLogManager.reader returns new TestLearnLogReader
    testLogManager.writer returns testLogWriter

    val we1 = mock[NextWordEntrySingleSource[String]]
    we1.word returns "test"
    val we2 = mock[NextWordEntrySingleSource[String]]
    we2.word returns "foo"
    val we3 = mock[NextWordEntrySingleSource[String]]
    we3.word returns "bar"

    val model = mock[AppendableNgramModel[String]]
    model.generateRandom() returns List(we1, we2, we3)

    val splitter:Splitter = mock[Splitter].split(anyString) returns List("sp", "lit")
    val joiner:Joiner = mock[Joiner].join(any) returns "Joined."

    val chatter = new Chatter(model, testLogManager, splitter, joiner, testLearnSourceStore)

    "Access log reader after instantiated" in {
      there was one(testLogManager).reader
    }
    "Call learn for each input log record" in {
      there were inputLogSize.times(model).learn(any, any)
    }
    "Call split for each input log record" in {
      there were inputLogSize.times(splitter).split("Жмур повесился в гроб, похрипев.")
    }
    "Create learnSource for each input log record" in {
      there were inputLogSize.times(testLearnSourceStore).create()
    }
    "Generate random" in {
      val generatedSentence = chatter.generateRandom()
      there was one(model).generateRandom()
      there was one(joiner).join(List("test", "foo", "bar"))
    }
    "Support learn()" in {
      chatter.learn("Uhod iz zhizni.")
    }
    "Called learn on model when calling learn" in {
      there was (inputLogSize+1).times(model).learn(any, any)
    }
    "Written learned text to log when calling learn" in {
      there was one(testLogWriter).write(any)
    }
  }
}
