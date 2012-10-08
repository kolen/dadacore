package dadacore.model

import dadacore.learnsource.LearnSentence

trait AppendableModel[Word] extends Model[Word] {
  def learn(text: Seq[Word], learn_sentence:LearnSentence)
}
