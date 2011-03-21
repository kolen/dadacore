package dadacore.web

import org.scalatra.ScalatraServlet

class Servlet extends ScalatraServlet {
  get("/") {
    <h1>Hello, world!</h1>
  }
}