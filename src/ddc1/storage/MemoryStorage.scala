package ddc1.storage.memory

import ddc1.storage._
import collection.mutable.{HashSet, HashMap}

class MemoryStorage extends Storage {
  class MemoryStorageConnection extends StorageConnection {
    private val entries = new HashMap[Array[String], HashSet[Array[String]]] ()
    private val root = new HashSet[Array[String]] ()

    class MemoryStorageWordState (raw_words: Array[String]) extends WordState  {
      def words = raw_words.toList
      def next_all = entries.get(raw_words) match {
        case set:HashSet[Array[String]] => set.toList.map(st => new MemoryStorageWordState(st))
        case None => throw new StateDoesNotExistInModel()
      }
    }

    private object starting_state_ extends StartingState {
      def next_all = root.toList.map(strs => new MemoryStorageWordState(strs))
    }

    def lookupState(words: List[String]):Option[State] = {
      assert(words.length == order)
      val raw_words = words.toArray
      entries.contains(raw_words) match {
        case true => new Some(new MemoryStorageWordState(raw_words))
        case false => None
      }
    }

    def markState(words: List[Option[String]]) = {
      assert(words.length == order+1)
      words match {
        case None :: state_words => root.add(state_words)
        case state_words :: None =>
          val state1 = entries.get(state_words) match {
            case None => throw MarkTransitionStateDoesNotExistException()
            case entry:HashSet => entry.add()
          }
      }


    }

    def is_newly_created = true

    def starting_state = starting_state_
  }
}
