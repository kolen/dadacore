package dadacore.chatter

import dadacore.model.{AppendableNgramModel, NextWordEntry}
import dadacore.text.{Splitter, Joiner}
import dadacore.learnsource.LearnSourceStore
import dadacore.learnlog.{LearnLogElement, LearnLogManager}
import java.util.Date
import com.google.inject.Inject

class Chatter @Inject() (
    model: AppendableNgramModel[String],
    logManager: LearnLogManager,
    splitter: Splitter,
    joiner: Joiner,
    learnSourceStore: LearnSourceStore) {

  class ChatterGeneratedSentence(val text: String, val words: Seq[NextWordEntry[String]]) extends GeneratedSentence

  def learn(text:String, source:String="", user:String="", date:Date = new Date()) {
    model.learn(splitter.split(text), learnSourceStore.create())
    logManager.writer.write(LearnLogElement(date, user, source, text))
  }

  def generateRandom(): GeneratedSentence = {
    val random = model.generateRandom()
    new ChatterGeneratedSentence(joiner.join(random.map(n=>n.word)), random)
  }

  for (e <- logManager.reader) {
    model.learn(splitter.split(e.text), learnSourceStore.create())
  }
}
