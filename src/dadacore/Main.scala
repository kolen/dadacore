package dadacore

import brain.Brain

object Main {
  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
    val input_iterator = io.Source.fromFile(args(0), "UTF-8").getLines()

    val br = new Brain
    for (line <- input_iterator) {
      br.learn(line)
    }

    for (i <- 0 until 100)
      println(br.generateRandom)
  }
}
