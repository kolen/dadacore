package dadacore.test.text

import org.junit.{Test,Assert}
import Assert._
import dadacore.text.Joiner

class JoinerTest {
  private val list1 = List("чирок", "-", "свистунок", " - ", "будущего", "нет", ".")
  private val joined1 = "Чирок-свистунок - будущего нет."

  private val list2 = List("драм", "это", "ненапряжная", "музыка", ", ", "простая", "для", "мозга")
  private val joined2 = "Драм это ненапряжная музыка, простая для мозга."

  @Test
  def testJoin1() {
    assertEquals(joined1, Joiner.join(list1))
  }

  @Test
  def testJoin2() {
    assertEquals(joined2, Joiner.join(list2))
  }
}