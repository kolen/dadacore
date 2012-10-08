package dadacore.model

import dadacore.learnsource.LearnSentence

trait NextWordEntrySingleSource[Word] extends NextWordEntry[Word] {
  def source: LearnSentence
}
