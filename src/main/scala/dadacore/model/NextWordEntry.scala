package dadacore.model

import dadacore.learnsource.LearnSentence

/** Represents next word (last element in ngram) related to specific context.
  *
  * @tparam Word Type of word, i.e. [[java.lang.String]].
  */
trait NextWordEntry[Word] {
  /** Returns word itself */
  def word: Word
  /** Returns probability of this word in context to which this NextWordEntry belongs */
  def prob: Double
}

trait NextWordEntryMultipleSource[Word] extends NextWordEntry[Word] {
  /** Returns sources from which this word in this context came */
  def sources: Seq[LearnSentence] // TODO: return probabilities too, maybe
}

trait NextWordEntrySingleSource[Word] extends NextWordEntry[Word] {
  /** Returns source from which this word in this context came */
  def source: LearnSentence
}
