package dadacore.model

import dadacore.learnsource.LearnSentence

trait NextWordEntryMultipleSource[Word] extends NextWordEntry[Word] {
  def sources: Seq[LearnSentence] // TODO: return probabilities too, maybe
}
