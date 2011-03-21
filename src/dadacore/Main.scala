package dadacore

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}
import dadacore.web.Servlet

object Main {
  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
    val server = new Server(8080)

    val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
    context.setContextPath("/")
    server.setHandler(context)
    context.addServlet(new ServletHolder(new Servlet),"/*")

    server.start
    server.join
  }
}
