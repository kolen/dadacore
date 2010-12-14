package dadacore

import brain.Brain

object Main {
  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
    val br = new Brain
    br.learn("Шульгин ваще сверхчеловек, почти все феники которые щас жрут это он придумал.")
    br.learn("купила вчера новог. украшение-ветку ели с красными яблоками и клюквой.и только сегодня доперла что на елях яблоки не растут.опять наебали.")
    br.learn("у меня в закладках рома жолудь и тёлка, у которой в альбоме \"альбомчик маленькой готэссы\"")
    br.learn("Чирок-свистунок взлетает вертикально, почти все чирки взлетают вертикально")

    for (i <- 0 until 100)
      println(br.generateRandom)
  }
}
