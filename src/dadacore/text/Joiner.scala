package dadacore.text

object Joiner {
  def join(words:List[String]):String = {
    val out = new StringBuilder()
    var last_was_point = true
    var last_was_word = false
    for (word <- words) {
      if (word charAt 0 isLetterOrDigit) {
        // word
        if (last_was_word) {
          out append ' '
        }

        if (last_was_point) {
          out append word.charAt(0).toUpper
          out append word.substring(1)
        } else {
          out append word
        }

        last_was_point = false
        last_was_word = true
      } else {
        // punctuation chars
        out append word

        last_was_point = word != "." && (word.indexOf('.') != -1)
        last_was_word = false
      }
    }

    out.toString
  }
}