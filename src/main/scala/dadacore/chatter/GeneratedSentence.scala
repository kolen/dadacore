package dadacore.chatter

import dadacore.model.NextWordEntry

trait GeneratedSentence {
  /**
   * Textual representation of generated sentence
   * @return
   */
  def text:String

  def words:Seq[NextWordEntry[String]]
}
