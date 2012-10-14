
package dadacore.model

/** Model supporting returning of all possible words (excluding 'any word' for
  * smoothing) in specified context, with their probabilities.
  */
trait ModelWithNext[Word] {
  /** Return all possible next words known by model, with probabilities, in given context.
    *
    * @param context Sequence of Words used to define context
    * @return [[dadacore.model.PossibleNextWords]] object listing known possible words with probabilities and
    *        learn source information
    */
  def next(context: Seq[Word]):PossibleNextWords[Word]
}
