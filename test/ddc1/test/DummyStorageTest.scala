package ddc1.test

import org.junit.{Test,Assert,Before,After}
import Assert._
import ddc1.storage.dummy.DummyStorageConnection
import ddc1.storage.{UnexistentState, ExistentState}

class DummyStorageTest {
  @Test
  def testTest {
    val ds = new DummyStorageConnection()
    assertTrue(ds.isInstanceOf[DummyStorageConnection])
    val st = ds.lookupStateByLiteral(List("Aaaa", "bbbb"))
    st match {
      case st:ExistentState => {

      }
      case st:UnexistentState => {

      }
    }
  }
}
