package dadacore.web

import org.scalatra.ScalatraServlet
import dadacore.brain.Brain

class DadacoreServlet extends ScalatraServlet {
  val brain = new Brain

  get("/") {
    contentType = "text/html"
    val wr = response.getWriter()
    for (i <- 0 until 50) {
      wr.append((<p>{brain.generateRandom}</p>).toString)
    }
  }
}