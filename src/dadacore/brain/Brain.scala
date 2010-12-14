package dadacore.brain

import dadacore.storage.memory.MemoryStorage
import collection.immutable.HashMap
import dadacore.composer.Composer
import dadacore.text.{Joiner, Splitter}

// TODO: add config

class Brain {
  val conn = MemoryStorage.connect(new HashMap())

  def learn(text: String) {
    val words = Splitter.split(text)
    if (words.length >= conn.order) {
      conn.markChain(words)
    }
  }
  def generateRandom:String = {
    val words = Composer.random(conn)
    Joiner.join(words)
  }
}
