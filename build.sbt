name := "dadacore"

version := "1.0-SNAPSHOT"

scalaVersion := "2.9.1"

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.0.4",
  "org.scalatra" %% "scalatra-scalate" % "2.0.4",
  // "org.scalatra" %% "scalatra-specs2" % "2.0.4" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
  "com.google.inject" % "guice" % "3.0",
  "org.eclipse.jetty" % "jetty-webapp" % "7.6.0.v20120127" % "container",
  "javax.servlet" % "servlet-api" % "2.5" % "provided"
)

libraryDependencies += "org.specs2" %% "specs2" % "1.12.1" % "test"

libraryDependencies += "org.mockito" % "mockito-all" % "1.9.0" % "test"

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                  "releases"  at "http://oss.sonatype.org/content/repositories/releases")

customConfiguration in container.Configuration := true

configurationXml in container.Configuration :=
  <Configure id="Server" class="org.eclipse.jetty.server.Server">
    <Set name="connectors">
      <Array type="org.eclipse.jetty.server.Connector">
        <Item>
          <New class="org.eclipse.jetty.server.ssl.SslSelectChannelConnector">
            <Arg>
              <New class="org.eclipse.jetty.http.ssl.SslContextFactory">
                <Set name="keyStore"><SystemProperty name="jetty.home" default="." />/test-server.keystore</Set>
                <Set name="keyStorePassword">keystore</Set>
                <Set name="keyManagerPassword">keystore</Set>
                <Set name="trustStore"><SystemProperty name="jetty.home" default="." />/test-server.keystore</Set>
                <Set name="trustStorePassword">keystore</Set>
              </New>
            </Arg>
            <Set name="port">8441</Set>
            <Set name="maxIdleTime">30000</Set>
          </New>
        </Item>
      </Array>
    </Set>
    <Set name="handler">
      <New class="org.eclipse.jetty.webapp.WebAppContext">
        <Set name="resourceBase"><SystemProperty name="jetty.home" default="."/>/src/main/webapp</Set>
        <Set name="descriptor"><SystemProperty name="jetty.home" default="."/>/src/main/webapp/WEB-INF/web.xml</Set>
        <Set name="contextPath">/</Set>
        <Set name="parentLoaderPriority">true</Set>
      </New>
    </Set>
    <Call name="addBean">
      <Arg>
        <New class="org.eclipse.jetty.security.HashLoginService">
          <Set name="name">dadacore</Set>
          <Call name="update">
            <Arg>test</Arg>
            <Arg>
              <New class="org.eclipse.jetty.util.security.Password"><Arg>test</Arg></New>
            </Arg>
            <Arg>
              <Array type="java.lang.String">
                <Item>dadacore</Item>
              </Array>
            </Arg>
          </Call>
          <Set name="refreshInterval">0</Set>
        </New>
      </Arg>
    </Call>
  </Configure>
