package ddc1.test

import org.junit.{Test,Assert,Before,After}
import Assert._
import ddc1.storage.dummy.DummyStorageConnection

class DummyStorageTest {
  @Test
  def testTest {
    val ds = new DummyStorageConnection()
    assertTrue(ds.isInstanceOf[DummyStorageConnection])
  }
}
