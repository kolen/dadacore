package dadacore.test.model

import org.specs2.mutable._
import dadacore.model._
import dadacore.learnsource.LearnSentence

object MemoryNgramModelSpec extends Specification {
  val sent1 = "Foo bar baz quux eggs .".split(" ")
  val sent2 = "Dong bar baz quux moo squeak crack".split(" ")
  object ls1 extends LearnSentence {}
  object ls2 extends LearnSentence {}

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
      next.words.map((n)=>n.word).toSet must_== Set("eggs", "moo")
      
      val next2 = m.next(new PrefixContext("Dong bar baz".split(" ")))
      next2.words must have length(1)
      next2.words.head.word must_== "quux"
    }
    "Return end of sentence" in {
      val m =  new MemoryNgramModel(3)
      m.learn(sent1, ls1)
      val next = m.next(List("bar", "baz", "quux"))
    }
  }
}
