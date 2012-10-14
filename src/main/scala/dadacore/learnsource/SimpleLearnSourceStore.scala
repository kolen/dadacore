package dadacore.learnsource

class SimpleLearnSourceStore extends LearnSourceStore {
  class MyLearnSentence extends LearnSentence
  def create() = new MyLearnSentence
}
