package dadacore.model

import dadacore.learnsource.LearnSentence

trait NextWordEntry[Word] {
  def word: Word
  def prob: Double
}

trait NextWordEntryMultipleSource[Word] extends NextWordEntry[Word] {
  def sources: Seq[LearnSentence] // TODO: return probabilities too, maybe
}

trait NextWordEntrySingleSource[Word] extends NextWordEntry[Word] {
  def source: LearnSentence
}
