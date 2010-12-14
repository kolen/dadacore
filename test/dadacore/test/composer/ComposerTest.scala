package dadacore.test.composer

import org.junit.{Before, Test, Assert}
import Assert._
import dadacore.storage.StorageConnection
import dadacore.storage.memory.MemoryStorage
import collection.immutable.HashMap
import dadacore.composer.Composer

class ComposerTest {
  private var conn:StorageConnection = null
  @Before
  def setUp {
    conn = MemoryStorage.connect(new HashMap())
  }

  @Test
  def test1 {
    conn.markChain(List("труп", "валяется", "."))
    assertEquals(List("труп", "валяется", "."), Composer.random(conn))
  }
}