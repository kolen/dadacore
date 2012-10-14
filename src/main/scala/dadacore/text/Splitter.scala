package dadacore.text

/** Splitter splits string of text into words that usually intended to use to learn model */
trait Splitter {
  def split(text:String): Seq[String]
}
