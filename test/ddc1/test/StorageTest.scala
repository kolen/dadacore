package ddc1.test

import org.junit.{Test,Assert}
import Assert._
import ddc1.storage.{AvailableNextState, State, WordState, StorageConnection}

abstract class StorageTest {
  protected var conn:StorageConnection = null
  protected val seq1 = List("anas", "crecca", "is", "a", "common", "and", "widespread", "duck", "which", "breeds", "in",
      "temperate", "eurasia", "and", "migrates", "south", "in", "winter", ".") // 19
  protected val seq2 = List("what", "is", "a", "common", "law", "marriage", "?") // 7

  @Test
  def testFillCheck {
    conn.markChain(seq1)
    val next_all_at_root = conn.starting_state.next_all
    assertEquals(1, next_all_at_root.length)
    next_all_at_root.head match {
      case x:WordState =>
        assertEquals(List("anas", "crecca", "is"), x.words)
        val next_all_2 = x.next_all
        assertEquals(1, next_all_2.length)
        next_all_2.head match {
          case x:WordState =>
            assertEquals(List("crecca", "is", "a"), x.words)
            assertEquals(1, x.next_all.length)
          case _ => fail("Not WordState returned")
        }
      case _ => fail("Not WordState returned")
    }

    val state_in_middle = conn.lookupState(List("common", "and", "widespread"))
    state_in_middle match {
      case Some(x:WordState) =>
        val next = x.next_all
        assertEquals(1, next.length)
      case _ =>
        fail("Not WordState returned")
    }
  }

  def traverseAll(state:State) {
    state match {
      case s:AvailableNextState =>
        val next_all = s.next_all
        assertTrue(next_all.length != 0)
        for (nextstate <- next_all) traverseAll(nextstate)
      case _ => null
    }
  }

  @Test
  def testTraverse1 {
    conn.markChain(seq1)
    traverseAll(conn.starting_state)
  }

  @Test
  def testSplit {
    conn.markChain(seq1)
    conn.markChain(seq2)
    traverseAll(conn.starting_state)

    assertEquals(2, conn.starting_state.next_all.length)

    val splitting_state = conn.lookupState(List("is", "a", "common"))
    splitting_state match {
      case Some(s:AvailableNextState) =>
        assertEquals(2, s.next_all.length)
      case None =>
        fail("No state")
      case _ =>
        fail("Non-availablenext state")
    }

    val nonsplitting_state = conn.lookupState(List("widespread", "duck", "which"))
    nonsplitting_state match {
      case Some(s:AvailableNextState) =>
        assertEquals(1, s.next_all.length)
      case None =>
        fail("No state")
      case _ =>
        fail("Non-availablenext state")
    }
  }
}