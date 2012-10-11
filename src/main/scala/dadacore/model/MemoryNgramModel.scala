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
  val startingNgrams = mutable.HashMap[Seq[String], Long]()

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

  def next(context: Context) = context match {
    case context: PrefixContext[String] =>
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
    case StartOfSentenceContext => null
    case _ => throw new IllegalArgumentException("Such context is not supported")
  }
  
  def learn(text: Seq[String], learnSentence: LearnSentence) {
    appendWordsAtStart(text.take(order), learnSentence)

    @tailrec
    def learnInner(text: Seq[String]) {
      if (text.length >= order+1) {
        appendWord(text.take(order), Some(text(order)), learnSentence)
        learnInner(text.drop(1))
      } else if (text.length == order) {
        appendWord(text.take(order), None, learnSentence)
      }
    }
    learnInner(text)
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
          case 1 => withword(0).newOccurrenceFromSource(learnSentence)
          case _ => throw new IntegrityError()
        }
        withoutword ++ List(newEntry)
      }
    })
  }

  protected def appendWordsAtStart(words: Seq[String], learnSentence: LearnSentence) {
    startingNgrams.put(words, startingNgrams.get(words) match {
        case None => 1
        case Some(i:Long) => i+1
      })
  }
}
