package dadacore.model

import util.Random

class PossibleNextWords[Word] (val words:IndexedSeq[NextWordEntry[Word]]) {
  def randomUniform(): NextWordEntry[Word] = words(Random.nextInt(words.length))
  //def randomModel(): NextWordEntry[Word] TODO: implement randomModel
}
