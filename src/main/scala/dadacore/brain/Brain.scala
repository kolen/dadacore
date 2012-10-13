package dadacore.brain

import dadacore.storage.memory.MemoryStorage
import collection.immutable.HashMap
import dadacore.composer.Composer
import dadacore.text.{RegularJoiner, RegularSplitter}
import dadacore.learnlog.{LearnLogNoSuchLogError, FileLearnLogWriter, FileLearnLogReader}

// TODO: add config

class Brain {
  val log_filename = "learnlog.log"

  lazy val conn = MemoryStorage.connect(new HashMap())
  lazy val log_writer = new FileLearnLogWriter("learnlog.log")

  // currently always replays log on init, should be configurable
  try {
    replayLog(log_filename)
  } catch {
    case _:LearnLogNoSuchLogError => Nil
  }

  def learn(text:String) { learn("-", text) }
  def learn(user:String, text:String) { learn(user, "none", text) }
  def learn(user:String, source:String, text: String) {
    if (learn_to_model(text))
      log_writer.write(user, source, text)
  }

  private def learn_to_model(text:String):Boolean = {
    val words = RegularSplitter.split(text)
    if (words.length >= conn.order) {
      conn.markChain(words)
      true
    } else {
      false
    }
  }

  def generateRandom:String = {
    val words = Composer.random(conn)
    RegularJoiner.join(words)
  }

  def replayLog(filename: String) {
    val log_reader = new FileLearnLogReader(filename)
    for (log_element <- log_reader) {
      learn_to_model(log_element.text)
    }
  }
}
