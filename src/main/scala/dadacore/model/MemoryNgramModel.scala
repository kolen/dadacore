package dadacore.model

import dadacore.learnsource.LearnSentence
import collection.mutable
import annotation.tailrec

class MemoryNgramModel (order:Int) extends AppendableModel[String] {
  abstract class NextEntry {
    def word: String
    def counts: Long
    def newOccurenceFromSource(new_source: LearnSentence): NextEntry
  }
  case class NextEntrySingle (word: String, counts: Long, source: LearnSentence) extends NextEntry {
    def newOccurenceFromSource(new_source: LearnSentence) = if
      (new_source == source) NextEntrySingle(word, counts+1, source)
      else NextEntryMultiple(word, counts+1, Array(source, new_source))
  }
  case class NextEntryMultiple (word: String, counts: Long, sources: Array[LearnSentence]) extends NextEntry {
    def newOccurenceFromSource(new_source: LearnSentence) = NextEntryMultiple(word, counts+1, sources)
  }

  class IntegrityError extends Exception

  val dictionary = mutable.HashMap[Array[String], Array[NextEntry]]()

  /**
   * Evaluate the probability of this word in this context.
   *
   * @param word Word
   * @param context Context
   * @return
   */
  def prob(word: String, context: Context[String]) = 0.0

  /**
   * Evaluate the (negative) log probability of this word in this context.
   *
   * @param word Word
   * @param context Context
   * @return
   */
  def logprob(word: String, context: Context[String]) = 0.0

  /**
   * Evaluate the total entropy of a message with respect to the model.
   * This is the sum of the log probability of each word in the message.
   *
   * @param text Sequence of words (text) to measure entropy against
   * @return value of entropy
   */
  def entropy(text: Seq[String]) = 0.0

  def next(context: Context[String]) = null

  @tailrec
  final def learn(text: Seq[String], learn_sentence: LearnSentence) {
    if (text.length >= order+1) {
      appendWord(text.take(order), text(order), learn_sentence)
      learn(text.drop(1), learn_sentence)
    }
  }

  protected def appendWord(context:Seq[String], word:String, learn_sentence:LearnSentence) {
    dictionary.put(context.toArray, dictionary.get(context.toArray) match {
      case None =>
        Array(NextEntrySingle(word, 1, learn_sentence))
      case Some(next_entries) => {
        val (withword, withoutword) = next_entries.partition((e:NextEntry)=>e.word == word)
        val new_entry:NextEntry = withword.length match {
          case 0 => NextEntrySingle(word, 1, learn_sentence)
          case 1 => withword(0).newOccurenceFromSource(learn_sentence)
          case _ => throw new IntegrityError()
        }
        (withoutword ++ List(new_entry)).toArray
      }
    })
  }
}
