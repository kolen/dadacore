package dadacore.learnsource

/** Store for registering [[dadacore.learnsource.LearnSentence]] objects.
  *
  * When system is learned by text supplied by user, each of input text lines gets
  * [[dadacore.learnsource.LearnSentence]] object associated with it. LearnSourceStore stores such objects and
  * allows to allocate new for new text sentences.
  */
trait LearnSourceStore {
  /** Allocates new [[dadacore.learnsource.LearnSentence]] and returns it. */
  def create():LearnSentence
}
