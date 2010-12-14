package dadacore.test.text

import org.junit.{Test,Assert}
import Assert._
import dadacore.text.Joiner

class JoinerTest {
  private val list1 = List("чирок", "-", "свистунок", " - ", "будущего", "нет", ".")
  private val joined1 = "Чирок-свистунок - будущего нет."

  @Test
  def testJoin1 {
    assertEquals(joined1, Joiner.join(list1))
  }
}