package dadacore.model

import util.Random

/** Possible next words in context
  *
  * Represents next words in context known by model. Each word is [[dadacore.model.NextWordEntry]], containing
  * information about probability and sources of this word.
  *
  * @param words
  * @tparam Word
  */
class PossibleNextWords[Word] (val words:IndexedSeq[NextWordEntry[Word]]) {
  def randomUniform(): NextWordEntry[Word] = words(Random.nextInt(words.length))
  //def randomModel(): NextWordEntry[Word] TODO: implement randomModel
}
