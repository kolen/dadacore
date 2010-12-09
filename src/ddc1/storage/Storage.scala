/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ddc1.storage

trait Word {
  def literal: String
}

trait State
trait AvailableNextState extends State {
  def next_all: List[State]
  def next_random: State
}
trait ExistentState
trait UnexistentState
trait ContinuableState extends ExistentState with AvailableNextState {
  def addLink(next: State)
}
trait StartingState extends AvailableNextState with ExistentState
trait EndingState extends State with ExistentState
trait WordState extends AvailableNextState {
  def words: List[Word]
}

trait StorageConnection {
  def is_newly_created: Boolean
  def starting_state: StartingState
  def lookupStateByLiteral(words: List[String])
  def lookupStateByWords(words: List[Word])
}
