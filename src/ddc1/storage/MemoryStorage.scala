package ddc1.storage.memory

import ddc1.storage._
import collection.mutable.{HashSet, HashMap}
object MemoryStorage extends Storage {
  class MemoryStorageConnection extends StorageConnection {
    type NextEntriesSet = HashSet[Option[Array[String]]]
    private val entries = new HashMap[Array[String], NextEntriesSet]()
    private val root = new HashSet[Array[String]] ()

    object starting_state extends StartingState {
      def next_all = root.toList.map(strs => new MemoryStorageWordState(strs))
    }
    private object ending_state extends EndingState

    class MemoryStorageWordState (raw_words: Array[String]) extends WordState  {
      def words = raw_words.toList
      def next_all = entries.get(raw_words) match {
        case Some(set) => set.toList.map(
          st => st match {
            case Some(x) => new MemoryStorageWordState(x)
            case None => ending_state
          })
        case None => throw new StateDoesNotExistInModel()
      }
    }

    def lookupState(words: List[String]):Option[State] = {
      if (words.length != order) {
        throw new IllegalArgumentException("List must contain "+order+" words (same as model order)")
      }
      val raw_words = words.toArray
      entries.contains(raw_words) match {
        case true => new Some(new MemoryStorageWordState(raw_words))
        case false => None
      }
    }

    def markChain(words: List[String]) = {
      if (words.length < order) {
        throw new IllegalArgumentException("Length of mark chain must be at least model order="+order)
      }

      def getOrCreateEntry(words: List[String]):NextEntriesSet = {
        assert(words.length == order)
        val raw_words_l = words.toArray
        entries.get(raw_words_l) match {
          case Some(x) => x
          case None => {
            val new_e = new NextEntriesSet()
            entries.put(raw_words_l, new_e)
            new_e
          }
        }
      }

      def markWordTransitions(words: List[String]) {
        if (words.length == order + 1) {
          getOrCreateEntry(words.take(order)).add(Some(words.takeRight(order).toArray))
        } else {
          markWordTransitions(words.take(order + 1))
          markWordTransitions(words.slice(1, order + 2))
        }
      }

      root.add(words.take(order).toArray)
      markWordTransitions(words)
      getOrCreateEntry(words.takeRight(order)).add(None)
    }

    def is_newly_created = true
  }

  def connect(params: Map[String, String]) = new MemoryStorageConnection()
}
