package dadacore.test.model

import org.specs2.mutable._
import dadacore.model.MemoryNgramModel
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
    }
  }
}
