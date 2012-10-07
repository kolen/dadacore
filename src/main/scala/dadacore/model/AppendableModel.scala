package dadacore.model

import dadacore.learnsource.LearnSource

trait AppendableModel[Word] extends Model[Word] {
  def append(text: Seq[Word], learn_source:LearnSource)
}
