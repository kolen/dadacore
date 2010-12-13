package ddc1.storage.memory

import ddc1.storage._
import collection.mutable.{HashSet, HashMap}

class MemoryStorage extends Storage {
  class MemoryStorageConnection extends StorageConnection {
    private val entries = new HashMap[Vector[String], HashSet[Vector[String]]] ()
    private val root = new HashSet[Vector[String]] ()

    class MemoryStorageWordState (rawwords: Vector[String]) extends WordState  {
      def words = rawwords.toList
      def next_all = entries.get(rawwords).toList.map(st => new MemoryStorageWordState(st))
    }

    private object starting_state_ extends StartingState {
      def next_all = root.toList.map(strs => new MemoryStorageWordState(strs))
    }

    def lookupState(words: List[String]):Option[State] = {
      assert(words.length == order)
      entries.get(words) match {
        case entry:Vector[String] => new MemoryStorageWordState(entry)
        case None => None
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
