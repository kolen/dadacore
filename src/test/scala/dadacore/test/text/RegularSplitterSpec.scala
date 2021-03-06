package dadacore.test.text

import org.specs2._
import dadacore.text.RegularSplitter

object RegularSplitterSpec extends Specification {
  
  private val text1 = "Чирок-свистунок - будущего нет."
  private val result1 = "чирок|-|свистунок| - |будущего| |нет".split('|')
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
  private val result3 = "hanging| |is| |the| |prevalent| |means| |of| |suicide".split('|')
  private val text4 = """
    Блоггерка nordica1488 слушала current93 в 12:00 и у нее произошел ывсоР87рЫ72оч52 4 scывс34соам7a.
    """
  private val result4 = ("блоггерка| |nordica1488| |слушала| |current93| |в| |12|:|00| |и| |у| |нее| |произошел|" +
      " |ывсор87ры72оч52| |4| |scывс34соам7a").split('|')

  def is =
    "RegularSplitter should" ^
      "Correctly split sample 1" !{ RegularSplitter.split(text1) ==== result1 }^
      //"Correctly split sample 2" !{ RegularSplitter.split(text2) === result2 }^
      "Correctly split sample 3" !{ RegularSplitter.split(text3) ==== result3 }^
      "Correctly split sample 4" !{ RegularSplitter.split(text4) ==== result4 }^
      "Handle empty string"      !{ RegularSplitter.split("") ==== Nil }^
      "Handle string of spaces"  !{ RegularSplitter.split("   ") ==== Nil }^
      "Handle sparse letters"    !{ RegularSplitter.split("  а   б в     ") ==== "а| |б| |в".split('|') }^
  end
}