package dadacore.web

import org.scalatra.ScalatraServlet
import com.google.inject.Guice
import dadacore.DadacoreModule
import dadacore.chatter.Chatter
import org.scalatra.scalate.ScalateSupport
import dadacore.model.ModelIsEmptyException

class DadacoreServlet extends ScalatraServlet with ScalateSupport {
  val injector = Guice.createInjector(new DadacoreModule)
  val chatter = injector.getInstance(classOf[Chatter])

  get("/") {
    contentType = "text/html"
    val generated = try {
      List.fill(50){chatter.generateRandom().text}
    } catch {
      case e:ModelIsEmptyException => Nil
    }

    jade("/index", "title" -> "test", "generatedSentences" -> generated)
  }

  post("/api/learn") {
    val text = request.getParameter("text")
    val lines = text.split("\\s*(\\r|\\n)\\s*")
    for (line <- lines)
      chatter.learn(line)
    redirect("/")
  }
}