package dadacore.web

import org.scalatra.ScalatraServlet
import com.google.inject.Guice
import dadacore.DadacoreModule
import dadacore.chatter.Chatter

class DadacoreServlet extends ScalatraServlet {
  val injector = Guice.createInjector(new DadacoreModule)
  val chatter = injector.getInstance(classOf[Chatter])

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
        <p>{chatter.generateRandom().text}</p>
      }
    </div>
    </body>
    </html>
  }

  post("/api/learn") {
    val text = request.getParameter("text")
    val lines = text.split("\\s*(\\r|\\n)\\s*")
    for (line <- lines)
      chatter.learn(line)
    redirect("/")
  }
}