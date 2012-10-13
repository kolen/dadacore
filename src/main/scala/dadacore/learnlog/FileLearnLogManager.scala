package dadacore.learnlog

class FileLearnLogManager (filename: String) extends LearnLogManager {
  lazy val writer = new FileLearnLogWriter(filename)

  /**
   * Return reader to read all learn log from starting
   * @return reader that allows to iterate over learn log from its start
   */
  def reader = new FileLearnLogReader(filename)
}
