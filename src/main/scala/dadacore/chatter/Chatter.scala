package dadacore.chatter

import dadacore.model.{AppendableNgramModel, NextWordEntry}
import dadacore.text.{Splitter, Joiner}
import dadacore.learnsource.LearnSourceStore
import dadacore.learnlog.{LearnLogElement, LearnLogManager}
import java.util.Date
import com.google.inject.Inject

/** Chatbot-like structure with support to learn on input text sentences and generating new.
  *
  * @param model String n-gram model with dynamic appending used for learning on sentences and generating random.
  * @param logManager Log manager. Model will be learned on texts from log when created and will write new sentences
  *                   inputted by user to the same log managed by this log manager.
  * @param splitter Instance of [[dadacore.text.Splitter]] used to split input text to feed to model.
  * @param joiner Instance of [[dadacore.text.Joiner]] used to join words generated by model into outputted texts.
  * @param learnSourceStore A [[dadacore.learnsource.LearnSourceStore]] to allocate
  *                         [[dadacore.learnsource.LearnSentence]] for each new text sentence received from input or
  *                         log.
  */
class Chatter @Inject() (
    model: AppendableNgramModel[String],
    logManager: LearnLogManager,
    splitter: Splitter,
    joiner: Joiner,
    learnSourceStore: LearnSourceStore) {

  class ChatterGeneratedSentence(val text: String, val words: Seq[NextWordEntry[String]]) extends GeneratedSentence

  /** Learn new text sentence.
    *
    * Will learn on specified text sentence and write it into learn log using associated
    * [[dadacore.learnlog.LearnLogManager]].
    *
    * @param text Text to learn.
    * @param source Name of source where text came from.
    * @param user Username of user supplied this text.
    * @param date Date when text sentence is received. Current date and time by default.
    */
  def learn(text:String, source:String="", user:String="", date:Date = new Date()) {
    val splitted = splitter.split(text)
    if (!splitted.isEmpty) {
      model.learn(splitted, learnSourceStore.create())
      logManager.writer.write(LearnLogElement(date, user, source, text))
    }
  }

  /** Generate random sentence of text. */
  def generateRandom(): GeneratedSentence = {
    val random = model.generateRandom()
    new ChatterGeneratedSentence(joiner.join(random.map(n=>n.word)), random)
  }

  for (e <- logManager.reader) {
    model.learn(splitter.split(e.text), learnSourceStore.create())
  }
}
