package dadacore.text

object Joiner {
  def join(words:List[String]):String = {
    val out = new StringBuilder()
    var last_was_end_of_sentence = true
    var last_was_word = false
    for (word <- words) {
      if (word charAt 0 isLetterOrDigit) {
        // word
        if (last_was_word) {
          out append ' '
        }

        if (last_was_end_of_sentence) {
          out append word.charAt(0).toUpper
          out append word.substring(1)
        } else {
          out append word
        }

        last_was_end_of_sentence = false
        last_was_word = true
      } else {
        // punctuation chars
        out append word

        last_was_end_of_sentence = word != "." && (word.matches("[?!.]"))
        last_was_word = false
      }
    }

    if (last_was_word)
      out append '.'

    out.toString
  }
}