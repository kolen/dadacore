package dadacore.model

trait PossibleNextWords[Word] extends Seq[Word] {
  def randomUniform(): NextWordEntry[Word]
  def randomModel(): NextWordEntry[Word]
}
