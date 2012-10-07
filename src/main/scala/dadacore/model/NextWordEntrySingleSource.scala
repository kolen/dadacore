package dadacore.model

import dadacore.learnsource.LearnSource

trait NextWordEntrySingleSource[Word] extends NextWordEntry[Word] {
  def source: LearnSource
}
