package dadacore.test.text

import dadacore.text.Splitter
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
  private val text3 = "Hanging is the prevalent means of suicide"
  private val result3 = List("hanging", "is", "the", "prevalent", "means", "of", "suicide")
  private val text4 = """
    Блоггерка nordica1488 слушала current93 в 12:00 и у нее произошел ывсоР87рЫ72оч52 4 scывс34соам7a.
    """
  private val result4 = List(
    "блоггерка", "nordica1488", "слушала", "current93", "в", "12", ":", "00", "и", "у", "нее", "произошел",
    "ывсор87ры72оч52", "4", "scывс34соам7a", "."
  )

  @Test
  def testSplit1 {
    assertEquals(result1, Splitter.split(text1))
  }

  @Test
  def testSplit2 {
    assertEquals(result2, Splitter.split(text2))
  }

  @Test
  def testSplit3 {
    assertEquals(result3, Splitter.split(text3))
  }

  @Test
  def testSplit4 {
    assertEquals(result4, Splitter.split(text4))
  }

  @Test
  def testSpecialCases {
    assertEquals(List(), Splitter split "")
    assertEquals(List(), Splitter split "    ")
  }

  @Test
  def testStrange {
    assertEquals(List("а", "б", "в"), Splitter split "  а   б в     ")
  }
}