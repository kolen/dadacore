package dadacore.storage.memory

import dadacore.storage._
import collection.mutable.{HashSet, HashMap}
import scala.annotation.tailrec

object MemoryStorage extends Storage {
  class MemoryStorageConnection extends StorageConnection {
    type StateWords = List[String]
    type NextEntriesSet = HashSet[Option[StateWords]]

    private val entries = new HashMap[StateWords, NextEntriesSet]()
    private val root = new HashSet[StateWords] ()

    object startingState extends StartingState {
      def nextAll = root.toList.map(strs => new MemoryStorageWordState(strs))
    }
    private object ending_state extends EndingState

    class MemoryStorageWordState (raw_words: StateWords) extends WordState  {
      def words = raw_words.toList
      def nextAll = entries.get(raw_words) match {
        case Some(set) => set.toList.map(
          st => st match {
            case Some(x) => new MemoryStorageWordState(x)
            case None => ending_state
          })
        case None => throw new StateDoesNotExistInModel()
      }
    }

    def lookupState(words: Seq[String]):Option[State] = {
      if (words.length != order) {
        throw new IllegalArgumentException("List must contain "+order+" words (same as model order)")
      }
      entries.contains(words.toList) match {
        case true => new Some(new MemoryStorageWordState(words.toList))
        case false => None
      }
    }

    def markChain(words: Seq[String]) {
      if (words.length < order) {
        throw new IllegalArgumentException("Length of mark chain must be at least model order="+order)
      }

      def getOrCreateEntry(words: List[String]):NextEntriesSet = {
        assert(words.length == order)
        entries.get(words) match {
          case Some(x) => x
          case None => {
            val new_e = new NextEntriesSet()
            entries.put(words, new_e)
            new_e
          }
        }
      }

      //@tailrec
      def markWordTransitions(words: List[String]) {
        assert(words.length >= order + 1)
        if (words.length == order + 1) {
          getOrCreateEntry(words.take(order)).add(Some(words.takeRight(order)))
        } else {
          markWordTransitions(words.take(order + 1))
          markWordTransitions(words.drop(1))
        }
      }

      val wl = words.toList
      root.add(wl.take(order))
      if (words.length != order) { // if only equals to order, just mark trans start -> word -> end, not word -> word
        markWordTransitions(wl)
      }
      getOrCreateEntry(wl.takeRight(order)).add(None)
    }

    def isNewlyCreated = true
  }

  def connect(params: Map[String, String]) = new MemoryStorageConnection()
}
