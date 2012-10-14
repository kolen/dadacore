package dadacore.model

/** Thrown when model is empty and some operation requires that model is not empty, such as generation of random
  * sentence.
  */
class ModelIsEmptyException extends Exception
