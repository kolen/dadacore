
package dadacore.model

/**
 * Model supporting returning of all possible words (excluding 'any word' for
 * smoothing) in specified context, with their probabilities.
 */
trait ModelWithNext[Word] {
  /**
   * Return all possible next words, with probabilities, in given context.
   */
  def next(context: PrefixContext[Word]):PossibleNextWords[Word]
  def next(context: Seq[Word]):PossibleNextWords[Word] =
    next(new PrefixContext[Word](context))
}
