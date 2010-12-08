package ddc1.storage.dummy

import ddc1.storage._

class DummyStorageConnection extends StorageConnection {
  class DummyEndingState extends EndingState
  class DummyStartingState extends StartingState {
    override def next_all = List()
    override def next_random = new DummyEndingState()
  }

  override def is_newly_created = true
  override def starting_state = new DummyStartingState
}
