package dadacore.model

trait NextWordEntry[Word] {
  def word: Option[Word]
  def prob: Double
}
