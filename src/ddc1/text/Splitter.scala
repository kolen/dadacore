package ddc1.text

import scala.util.matching.Regex

object Splitter {
  private val word_matcher = new Regex("""(?mx:
    ([\p{L}\p{N}]+)  # word
    |
    ([^\p{L}\p{N}]+) # punctuation
    )""")

  private val word_fixer = new Regex("\\s{2,}")

  def split(text:String): List[String] =
    word_matcher.findAllIn(text).matchData.map( m =>
      if (m.group(1) != null) {
        m.group(1).toLowerCase
      } else {
        m.group(2).toLowerCase
      }
    ).filter(x => x.trim != "").toList
}
