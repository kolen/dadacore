package dadacore.test.text

import org.specs2._
import dadacore.text.RegularJoiner

object RegularJoinerSpec extends Specification {
  private val list1 =  "чирок|-|свистунок| - |будущего| |нет|.|".split('|')
  private val joined1 = "Чирок-свистунок - будущего нет."

  private val list2 = "драм| |это| |ненапряжная| |музыка|, |простая| |для| |мозга|.|".split('|')
  private val joined2 = "Драм это ненапряжная музыка, простая для мозга."

  def is =
    "RegularJoiner should " ^
      "Join sample 1" !{ RegularJoiner.join(list1) === joined1 }^
      "Join sample 2" !{ RegularJoiner.join(list2) === joined2 }^
    end  
}