package dadacore.chatter

import dadacore.model.{AppendableNgramModel, NextWordEntry}
import java.util.logging.LogManager
import dadacore.text.{Splitter, Joiner}
import dadacore.learnsource.LearnSourceStore

class Chatter(
    model: AppendableNgramModel[String],
    logManager: LogManager,
    splitter: Splitter,
    joiner: Joiner,
    learnSourceStore: LearnSourceStore) {

  class ChatterGeneratedSentence(val text: String, val words: Seq[NextWordEntry[String]]) extends GeneratedSentence

  def learn(text: String) {
    model.learn(splitter.split(text), learnSourceStore.create())
  }

  def generateRandom(): GeneratedSentence = {
    val random = model.generateRandom()
    new ChatterGeneratedSentence(joiner.join(random.map(n=>n.word)), random)
  }
}
