package dadacore.learnlog

trait LearnLogManager {
  /**
   * Return reader to read all learn log from starting
   * @return reader that allows to iterate over learn log from its start
   */
  def reader:LearnLogReader

  /**
   * Return writer to write to learn log
   * @return writer that allows to append to learn log
   */
  def writer:LearnLogWriter
}
