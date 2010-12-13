package ddc1.test

import org.junit.{Test,Assert}
import Assert._
import ddc1.storage.{WordState, State, StorageConnection}

abstract class StorageTest {
  protected var conn:StorageConnection = null
  protected val seq1 = List("anas", "crecca", "is", "a", "common", "and", "widespread", "duck", "which", "breeds", "in",
      "temperate", "eurasia", "and", "migrates", "south", "in", "winter", ".")

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
      case x:WordState =>
        val next = x.next_all
        assertEquals(1, next.length)
      case _ => fail("Not WordState returned")
    }
  }
}