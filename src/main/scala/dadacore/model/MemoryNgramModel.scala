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
    def newOccurrenceFromSource(new_source: LearnSentence): NextEntry
  }
  case class NextEntrySingle (word: String, counts: Long, source: LearnSentence) extends NextEntry {
    def newOccurrenceFromSource(new_source: LearnSentence) = if
      (new_source == source) NextEntrySingle(word, counts+1, source)
      else NextEntryMultiple(word, counts+1, Array(source, new_source))
  }
  case class NextEntryMultiple (word: String, counts: Long, sources: Array[LearnSentence]) extends NextEntry {
    def newOccurrenceFromSource(new_source: LearnSentence) = NextEntryMultiple(word, counts+1, sources)
  }
  
  class MyNextWordEntrySingleSource (
      val word:String,
      val prob:Double,
      val source:LearnSentence)
    extends NextWordEntrySingleSource[String] {}

  class MyNextWordEntryMultipleSource (
      val word:String,
      val prob:Double,
      val sources:Seq[LearnSentence])
    extends NextWordEntryMultipleSource[String] {}

  class IntegrityError extends Exception
  
  object NoNextWords extends PossibleNextWords(Vector[NextWordEntry[String]]())

  val dictionary = mutable.HashMap[Seq[String], Seq[NextEntry]]()

  val terminatorWord = ""
  val padLeft = List.fill(order)(terminatorWord)
  val padRight = List(terminatorWord)

  /**
   * Evaluate the probability of this word in this context.
   *
   * @param word Word
   * @param context Context
   * @return
   */
  def prob(word: String, context: Seq[String]) = 0.0 // TODO: implement

  /**
   * Evaluate the (negative) log probability of this word in this context.
   *
   * @param word Word
   * @param context Context
   * @return
   */
  def logprob(word: String, context: Seq[String]) = 0.0 // TODO: implement

  /**
   * Evaluate the total entropy of a message with respect to the model.
   * This is the sum of the log probability of each word in the message.
   *
   * @param text Sequence of words (text) to measure entropy against
   * @return value of entropy
   */
  def entropy(text: Seq[String]) = 0.0 // TODO: implement

  override def next(context: Seq[String]) =
    dictionary.get(context) match {
      case None => NoNextWords
      case Some(ne:Seq[NextEntry]) => new PossibleNextWords(
        ne.toSeq.map(e => e match {
          case en:NextEntrySingle =>
            new MyNextWordEntrySingleSource(
              en.word,
              0, // TODO: prob
              en.source)
          case en:NextEntryMultiple =>
            new MyNextWordEntryMultipleSource(
              en.word,
              0, // TODO: prob
              en.sources)
        }
      ).toIndexedSeq)
    }

  def learn(text: Seq[String], learnSentence: LearnSentence) {
    @tailrec
    def learnInner(text: Seq[String]) {
      if (text.length >= order+1) {
        appendWord(text.take(order), text(order), learnSentence)
        learnInner(text.drop(1))
      }
    }
    learnInner(padLeft ++ text ++ padRight)
  }

  protected def appendWord(context:Seq[String], word:String, learnSentence:LearnSentence) {
    dictionary.put(context, dictionary.get(context) match {
      case None =>
        List(NextEntrySingle(word, 1, learnSentence))
      case Some(nextEntries) => {
        val (withWord, withoutWord) = nextEntries.partition((e:NextEntry)=>e.word == word)
        val newEntry:NextEntry = withWord.length match {
          case 0 => NextEntrySingle(word, 1, learnSentence)
          case 1 => withWord(0).newOccurrenceFromSource(learnSentence)
          case _ => throw new IntegrityError()
        }
        withoutWord ++ List(newEntry)
      }
    })
  }
}
