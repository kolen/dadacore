package dadacore.model

/** An [[http://en.wikipedia.org/wiki/N-gram N-gram]] model.
  *
  * @tparam Word Type used as word (i.e. [[java.lang.String]]).
  */
trait NgramModel[Word] extends ModelWithNext[Word] {
  /** Generate random sequence of Word instances using model, between terminal words (starting and ending).
    *
    * @return Generated sequence of [[dadacore.model.NextWordEntry]].
    */
  def generateRandom():Seq[NextWordEntry[Word]]
}

/** An [[http://en.wikipedia.org/wiki/N-gram N-gram]] model supporting dynamic learning on sequences of words
  *
  * @tparam Word Type used as word (i.e. [[java.lang.String]]).
  */
trait AppendableNgramModel[Word] extends NgramModel[Word] with AppendableModel[Word]