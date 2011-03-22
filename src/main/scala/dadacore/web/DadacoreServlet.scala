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
    <textarea name="text" />
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
    brain.learn(text)
    redirect("/")
  }
}