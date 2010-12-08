/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ddc1.storage

trait StorageConnection {
  def is_newly_created: Boolean
  def starting_state: StartingState
}

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
