/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dadacore.storage

import util.Random

class MarkTransitionStateDoesNotExistException extends Exception
class StateDoesNotExistInModel extends Exception
class DatabaseError extends Exception

trait State
trait AvailableNextState extends State {
  def nextAll: List[State]
  def nextRandom: State = Random.shuffle(nextAll).head
}
trait StartingState extends AvailableNextState
trait EndingState extends State
trait WordState extends AvailableNextState {
  def words: List[String]
}

trait StorageConnection {
  def isNewlyCreated: Boolean
  def startingState: StartingState
  def lookupState(words: Seq[String]): Option[State]

  def markChain(words: Seq[String])

  /**
   * Order of markov model. Currently only 3 is supported.
   */
  final def order = 2
}

trait Storage {
  def connect(params: Map[String, String]): StorageConnection
}