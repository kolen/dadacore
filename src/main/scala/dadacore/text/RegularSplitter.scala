package dadacore.text

import scala.util.matching.Regex

object RegularSplitter extends Splitter {
  private val word_matcher = new Regex("""(?mxs:
    ([\p{L}\p{N}]+)  # word
    |
    ([^\p{L}\p{N}]+) # punctuation
    )""")

  private val word_fixer = new Regex("\\s{2,}")

  def split(text:String): Seq[String] =
    word_matcher.findAllIn(text.trim).matchData.map( m =>
      if (m.group(1) != null) {
        m.group(1).toLowerCase
      } else {
        word_fixer.replaceAllIn(m.group(2), " ")
      }
    ).toList
}