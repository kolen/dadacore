package dadacore.learnlog

class LearnLogInvalidLineException(message:String) extends Exception

trait LearnLogReader extends Traversable[LearnLogElement]