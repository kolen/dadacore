package dadacore.model

trait NextWordEntry[Word] {
  def word: Word
  def prob: Double
}
