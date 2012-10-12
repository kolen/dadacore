package dadacore.text

import util.matching.Regex

object Joiner {
  private val sentence_end_regex = new Regex("[?!.\u2026]")

  def join(words:Seq[String]):String = {
    // TODO: rewrite, some code left from times when joiner added spaces between words
    val out = new StringBuilder()
    var last_was_end_of_sentence = true
    for (word <- words) {
      if (word.charAt(0).isLetterOrDigit) {
        if (last_was_end_of_sentence) {
          out append word.charAt(0).toUpper
          out append word.substring(1)
        } else {
          out append word
        }

        last_was_end_of_sentence = false
      } else {
        // punctuation chars
        out append word

        last_was_end_of_sentence = sentence_end_regex.findFirstIn(word).isDefined
      }
    }

    if (!last_was_end_of_sentence)
      out append '.'

    out.toString()
  }
}