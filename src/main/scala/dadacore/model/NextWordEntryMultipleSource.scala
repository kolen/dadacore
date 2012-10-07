package dadacore.model

import dadacore.learnsource.LearnSource

trait NextWordEntryMultipleSource[Word] extends NextWordEntry[Word] {
  def sources: Seq[LearnSource] // TODO: return probabilities too, maybe
}
