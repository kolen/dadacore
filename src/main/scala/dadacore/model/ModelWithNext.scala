
package dadacore.model

/**
 * Model supporting returning of all possible words (excluding 'any word' for
 * smoothing) in specified context, with their probabilities.
 */
trait ModelWithNext[Word] {
  def next(context: Context):PossibleNextWords[Word]
}
