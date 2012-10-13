package dadacore.model

trait NgramModel[Word] extends ModelWithNext[Word] {
  def generateRandom():Seq[NextWordEntry[Word]]
}

trait AppendableNgramModel[Word] extends NgramModel[Word] with AppendableModel[Word]