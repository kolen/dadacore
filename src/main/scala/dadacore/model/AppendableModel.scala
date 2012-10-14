package dadacore.model

import dadacore.learnsource.LearnSentence

/** Model that allows to learn dynamically on word sequences */
trait AppendableModel[Word] extends Model[Word] {
  /** Learn model on word sequence
    *
    * @param text Sequence of Word objects used to learn this model
    * @param learnSentence [[dadacore.learnsource.LearnSentence]] used to reference source of sentence used to learn
    *                     this model
    */
  def learn(text: Seq[Word], learnSentence:LearnSentence)
}
