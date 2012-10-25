package dadacore.text

object RegularJoiner extends Joiner {
  private val sentenceEndRegex = "[?!.\u2026]".r

  def join(words:Seq[String]):String = {
    // TODO: rewrite, some code left from times when joiner added spaces between words
    val out = new StringBuilder()
    var lastWasEndOfSentence = true
    for (word <- words) {
      if (word.charAt(0).isLetterOrDigit) {
        if (lastWasEndOfSentence) {
          out append word.charAt(0).toUpper
          out append word.substring(1)
        } else {
          out append word
        }

        lastWasEndOfSentence = false
      } else {
        // punctuation chars
        out append word

        lastWasEndOfSentence = sentenceEndRegex.findFirstIn(word).isDefined
      }
    }

    if (!lastWasEndOfSentence)
      out append '.'

    out.toString()
  }
}