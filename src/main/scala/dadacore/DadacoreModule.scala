package dadacore

import com.google.inject._
import learnlog.{FileLearnLogManager, LearnLogManager}
import learnsource.{SimpleLearnSourceStore, LearnSourceStore}
import model.{MemoryNgramModel, AppendableNgramModel}
import com.google.inject.name.Names
import text.{RegularSplitter, Splitter, Joiner, RegularJoiner}

/** Main Dadacore module describing interface bindings */
class DadacoreModule extends AbstractModule {
  def configure() {
    bind(classOf[Joiner]).toInstance(RegularJoiner)
    bind(classOf[Splitter]).toInstance(RegularSplitter)
    bind(new TypeLiteral[AppendableNgramModel[String]] {}).to(classOf[MemoryNgramModel])
    bind(classOf[LearnLogManager]).to(classOf[FileLearnLogManager])
    bind(classOf[LearnSourceStore]).to(classOf[SimpleLearnSourceStore])

    bind(classOf[Int]).annotatedWith(Names.named("ngram model order")).toInstance(4)
    //bind(classOf[String]).annotatedWith(Names.named("learn log filename")).toInstance(
    //  System.getenv("OPENSHIFT_DATA_DIR") + "/learn.log")

    bind(classOf[String]).annotatedWith(Names.named("learn log filename")).toInstance("learn.log")
  }
}
