package dadacore.composer

import annotation.tailrec
import dadacore.storage._

class ComposerException extends Exception

object Composer {
  def random(conn:StorageConnection):List[String] = {
    @tailrec
    def traverse(existing_words:List[String], state:State):List[String] = {
      state match {
        case s:WordState => traverse(existing_words :+ s.words.last, s.next_random)
        case _:EndingState => existing_words
      }
    }

    conn.starting_state.next_random match {
      case s:WordState => traverse(s.words, s)
      case _ => throw new ComposerException()
    }
  }
}