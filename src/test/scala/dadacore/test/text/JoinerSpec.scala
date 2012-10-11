package dadacore.test.text

import org.specs2._
import dadacore.text.Joiner

object JoinerSpec extends Specification {
  private val list1 = List("чирок", "-", "свистунок", " - ", "будущего", "нет", ".")
  private val joined1 = "Чирок-свистунок - будущего нет."

  private val list2 = List("драм", "это", "ненапряжная", "музыка", ", ", "простая", "для", "мозга")
  private val joined2 = "Драм это ненапряжная музыка, простая для мозга."

  def is =
    "Joiner should " ^
      "Join sample 1" !{ Joiner.join(list1) === joined1 }^
      "Join sample 2" !{ Joiner.join(list2) === joined2 }^
    end  
}