/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ddc1.storage

import java.util.Dictionary
import util.Random

class MarkTransitionStateDoesNotExistException extends Exception
class StateDoesNotExistInModel extends Exception

trait State
trait AvailableNextState extends State {
  def next_all: List[State]
  def next_random: State = Random.shuffle(next_all).head
}
trait StartingState extends AvailableNextState
trait EndingState extends State
trait WordState extends AvailableNextState {
  def words: List[String]
}

trait StorageConnection {
  def is_newly_created: Boolean
  def starting_state: StartingState
  def lookupState(words: List[String]): Option[State]

  /**
   * marks state transition creating states if necessary.
   */
  def markTransition(words: List[Option[String]])

  /**
   * Order of markov model. Currently only 3 is supported.
   */
  final def order = 3
}

trait Storage {
  def connect(params: Dictionary): StorageConnection
}