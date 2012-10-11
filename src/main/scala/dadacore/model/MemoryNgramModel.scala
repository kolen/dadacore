package dadacore.model

import dadacore.learnsource.LearnSentence
import collection.mutable
import annotation.tailrec

class MemoryNgramModel (order:Int) extends AppendableModel[String] 
                                      with ModelWithNext[String] 
{
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
  
  class MyNextWordEntrySingleSource (
      val word:Option[String],
      val prob:Double,
      val source:LearnSentence)
    extends NextWordEntrySingleSource[String] {}

  class MyNextWordEntryMultipleSource (
      val word:Option[String],
      val prob:Double,
      val sources:Seq[LearnSentence])
    extends NextWordEntryMultipleSource[String] {}

  class IntegrityError extends Exception
  
  object NoNextWords extends PossibleNextWords(List[NextWordEntry[String]]())

  val dictionary = mutable.HashMap[Seq[String], Seq[NextEntry]]()

  /**
   * Evaluate the probability of this word in this context.
   *
   * @param word Word
   * @param context Context
   * @return
   */
  def prob(word: String, context: Context) = 0.0

  /**
   * Evaluate the (negative) log probability of this word in this context.
   *
   * @param word Word
   * @param context Context
   * @return
   */
  def logprob(word: String, context: Context) = 0.0

  /**
   * Evaluate the total entropy of a message with respect to the model.
   * This is the sum of the log probability of each word in the message.
   *
   * @param text Sequence of words (text) to measure entropy against
   * @return value of entropy
   */
  def entropy(text: Seq[String]) = 0.0

  def next(context: PrefixContext[String]) = 
    dictionary.get(context.word_list) match {
      case None => NoNextWords
      case Some(ne:Seq[NextEntry]) => new PossibleNextWords(
        ne.toSeq.map((e) => e match {
          case en:NextEntrySingle =>
            new MyNextWordEntrySingleSource(
              if (en.word != "") Some(en.word) else None, 
              0, // TODO: prob
              en.source)
          case en:NextEntryMultiple =>
            new MyNextWordEntryMultipleSource(
              if (en.word != "") Some(en.word) else None,
              0, // TODO: prob
              en.sources)
        }
      ))
    }

  @tailrec
  final def learn(text: Seq[String], learnSentence: LearnSentence) {
    if (text.length >= order+1) {
      appendWord(text.take(order), Some(text(order)), learnSentence)
      learn(text.drop(1), learnSentence)
    } else if (text.length == order) {
      appendWord(text.take(order), None, learnSentence)
    }
  }

  protected def appendWord(context:Seq[String], word:Option[String], learnSentence:LearnSentence) {
    if (word.isDefined && word.get == "") throw new IllegalArgumentException("Word cannot be empty string")
    val wordToStore = word getOrElse ""
    dictionary.put(context, dictionary.get(context) match {
      case None =>
        List(NextEntrySingle(wordToStore, 1, learnSentence))
      case Some(nextEntries) => {
        val (withword, withoutword) = nextEntries.partition((e:NextEntry)=>e.word == word)
        val newEntry:NextEntry = withword.length match {
          case 0 => NextEntrySingle(wordToStore, 1, learnSentence)
          case 1 => withword(0).newOccurenceFromSource(learnSentence)
          case _ => throw new IntegrityError()
        }
        withoutword ++ List(newEntry)
      }
    })
  }
}
