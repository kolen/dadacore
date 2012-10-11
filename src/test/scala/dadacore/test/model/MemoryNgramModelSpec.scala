package dadacore.test.model

import org.specs2.mutable._
import dadacore.model._
import dadacore.learnsource.LearnSentence

object MemoryNgramModelSpec extends Specification {
  val sent1 = "Foo bar baz quux eggs .".split(" ")
  val sent2 = "Dong bar baz quux moo squeak crack".split(" ")
  val sent3 = "Faux crecca moo squeak crack sposob".split(" ")
  object ls1 extends LearnSentence {}
  object ls2 extends LearnSentence {}
  object ls3 extends LearnSentence {}

  "MemoryNgramModel" should {
    "Allow to be instantiated" in {
      val m =  new MemoryNgramModel(3)
    }
    "Learn on simple text sequence" in {
      val m =  new MemoryNgramModel(3)
      m.learn(sent1, ls1)
      val next = m.next(List("Foo", "bar", "baz"))
      next.words must have length(1)
      next.words.head.word must_== "quux"
      val next2 = m.next(List("bar", "baz", "quux"))
      next2.words must have length(1)
      next2.words.head.word must_== "eggs"
    }
    "Learn with branching" in {
      val m =  new MemoryNgramModel(3)
      m.learn(sent1, ls1)
      m.learn(sent2, ls2)
      val next = m.next(List("bar", "baz", "quux"))
      next.words must have length(2)
      next.words map { n => n.word } must contain("eggs", "moo").only

      val next2 = m.next("Dong bar baz".split(" "))
      next2.words must have length(1)
      next2.words.head.word must_== "quux"
    }
    "Return end of sentence" in {
      val m =  new MemoryNgramModel(3)
      m.learn(sent1, ls1)
      val next = m.next(List("quux", "eggs", "."))
      next.words must have length(1)
      next.words.head.word must_== ""
    }
    "Branch at end of sentence" in {
      val m =  new MemoryNgramModel(3)
      m.learn(sent2, ls2)
      m.learn(sent3, ls3)
      
      val next = m.next("moo squeak crack".split(" "))
      next.words map { n => n.word } must contain ("sposob", "").only
    }
    "Return next in context of start of sentence" in {
      val m =  new MemoryNgramModel(3)
      m.learn(sent1, ls1)
      
      val next = m.next(Nil)
    }
  }
}
