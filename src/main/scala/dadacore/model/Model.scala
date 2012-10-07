package dadacore.model

/**
 * A probabilistic model predicting probability that word occurs after
 * given sequence of words.
 *
 * @tparam Word Type for words in this model
 */
trait Model[Word] {
  /**
   * Evaluate the probability of this word in this context.
   *
   * @param word
   * @param context
   * @return
   */
  def prob(word:Word, context:Context[Word]): Double

  /**
   * Evaluate the (negative) log probability of this word in this context.
   *
   * @param word
   * @param context
   * @return
   */
  def logprob(word:Word, context:Context[Word]): Double

  /**
   * Evaluate the total entropy of a message with respect to the model.
   * This is the sum of the log probability of each word in the message.
   *
   * @param text Sequence of words (text) to measure entropy against
   * @return value of entropy
   */
  def entropy(text:Seq[Word]): Double

  def perplexity(text:Seq[Word]): Double = math.pow(2, entropy(text))

  def next(context:Context[Word]): PossibleNextWords[Word]
}
