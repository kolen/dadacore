/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ddc1.storage

import java.util.Dictionary

trait Word {
  def literal: String
}

trait State
trait AvailableNextState extends State {
  def next_all: List[State]
  def next_random: State
}
trait StartingState extends AvailableNextState
trait EndingState extends State
trait WordState extends AvailableNextState {
  def words: List[Word]
}

trait StorageConnection {
  def is_newly_created: Boolean
  def starting_state: StartingState
  def lookupState(words: List)

  /**
   * marks state transition creating states if necessary.
   */
  def markState(words: List)

  /**
   * Order of markov model. Currently only 3 is supported.
   */
  final def order = 3
}

trait Storage {
  def connect(params: Dictionary): StorageConnection
}