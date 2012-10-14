package dadacore.learnlog

import com.google.inject.Inject
import com.google.inject.name.Named

class FileLearnLogManager @Inject() (@Named("learn log filename") filename: String) extends LearnLogManager {
  class EmptyLearnLogReader extends LearnLogReader {
    def next() = null
    def hasNext = false
  }

  lazy val writer = new FileLearnLogWriter(filename)

  /**
   * Return reader to read all learn log from starting
   * @return reader that allows to iterate over learn log from its start
   */
  def reader = try {
    new FileLearnLogReader(filename)
  } catch {
    case e:LearnLogNoSuchLogError => new EmptyLearnLogReader
  }
}
