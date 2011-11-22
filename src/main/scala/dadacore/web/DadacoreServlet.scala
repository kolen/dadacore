package dadacore.web

import org.scalatra.ScalatraServlet
import dadacore.brain.Brain

class DadacoreServlet extends ScalatraServlet {
  val brain = new Brain

  get("/") {
    contentType = "text/html"
    <html>
    <head>
      <title>dadacore</title>
    </head>
    <body>
    <form method="post" action="/api/learn">
    <textarea name="text" cols="80" rows="10" />
    <button type="submit">ook</button>
    </form>
    <div>{
      for (i <- 0 until 50) yield
        <p>{brain.generateRandom}</p>
      }
    </div>
    </body>
    </html>
  }

  post("/api/learn") {
    val text = request.getParameter("text")
    val lines = text.split("\\s*(\\r|\\n)\\s*")
    for (line <- lines)
      brain.learn(line)
    redirect("/")
  }
}