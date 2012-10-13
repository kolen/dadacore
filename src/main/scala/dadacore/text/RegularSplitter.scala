package dadacore.text

import scala.util.matching.Regex

object RegularSplitter extends Splitter {
  private val wordMatcher = new Regex("""(?mxs:
    ([\p{L}\p{N}]+)  # word
    |
    ([^\p{L}\p{N}]+) # punctuation
    )""")

  private val wordFixer = new Regex("\\s{2,}")

  def split(text:String): Seq[String] =
    wordMatcher.findAllIn(text.trim).matchData.map( m =>
      if (m.group(1) != null) {
        m.group(1).toLowerCase
      } else {
        wordFixer.replaceAllIn(m.group(2), " ")
      }
    ).toList
}