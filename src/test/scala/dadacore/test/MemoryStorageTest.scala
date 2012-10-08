package dadacore.test

import org.junit.{Before, Test}
import dadacore.storage.memory.MemoryStorage
import collection.immutable.HashMap

class MemoryStorageTest extends StorageTest {
  @Before
  def setUp() {
    conn = MemoryStorage.connect(new HashMap[String, String]())
  }
}