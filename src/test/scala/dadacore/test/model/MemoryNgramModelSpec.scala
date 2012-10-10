package dadacore.test.model

import org.specs2.mutable._
import dadacore.model.{MemoryNgramModel, PrefixContext}
import dadacore.learnsource.LearnSentence

object MemoryNgramModelSpec extends Specification {
  val sent1 = "Foo bar baz quux eggs .".split(" ")

  "MemoryNgramModel" should {
    "Allow to be instantiated" in {
      val m =  new MemoryNgramModel(3)
    }
    "Learn on simple text sequence" in {
      val m =  new MemoryNgramModel(3)
      object ls extends LearnSentence {}
      m.learn(sent1, ls)
      val next = m.next(new PrefixContext(List("Foo", "bar", "baz")))
      next.words must have length(1)
      next.words.head.word must_== "quux"
      val next2 = m.next(new PrefixContext(List("bar", "baz", "quux")))
      next2.words must have length(1)
      next2.words.head.word must_== "eggs"
    }
  }
}
