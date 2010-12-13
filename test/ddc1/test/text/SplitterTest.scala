package ddc1.test.text

import ddc1.text.Splitter
import org.junit.{Test,Assert}
import Assert._

class SplitterTest {
  private val text1 = "Чирок-свистунок - будущего нет."
  private val result1 = List("чирок", "-", "свистунок", " - ", "будущего", "нет", ".")
  private val text2 = """
    — Ой! что это?!!!
    — Это пузи-блэкмиталист, тинки-винки.
    — Оооо! аааа!
    — Он укрепляет анал...
  """
  private val result2 = List(
    "— ", "ой", "! ", "что", "это", "?!!! — ",
    "это", "пузи", "-", "блэкмиталист", ", ", "тинки", "-", "винки", ". — ",
    "оооо", "! ", "аааа", "! — ",
    "он", "укрепляет", "анал", "..."
  )

  @Test
  def testSplit1 {
    assertEquals(result1, Splitter.split(text1))
  }

  @Test
  def testSplit2 {
    assertEquals(result2, Splitter.split(text2))
  }
}