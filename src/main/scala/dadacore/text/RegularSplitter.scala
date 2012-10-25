package dadacore.text

object RegularSplitter extends Splitter {
  private val wordMatcher = """(?mxs:
    ([\p{L}\p{N}]+)  # word
    |
    ([^\p{L}\p{N}]+) # punctuation
    )""".r

  private val wordFixer = "\\s{2,}".r

  private object SentenceAndPeriod {
    def unapply (l: Seq[String]) =
      if (l.nonEmpty) l.last match { case p if p.trim == "." => Some((l.init, p)) case _ => None }
      else None
  }

  def split(text:String): Seq[String] =
    wordMatcher.findAllIn(text.trim).matchData.map(m =>
      if (m.group(1) != null) {
        m.group(1).toLowerCase
      } else {
        wordFixer.replaceAllIn(m.group(2), " ")
      }
    ).toIndexedSeq match {
      case SentenceAndPeriod(withoutPeriod, _) => withoutPeriod
      case l => l
    }
}