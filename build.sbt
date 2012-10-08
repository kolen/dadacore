name := "dadacore"

version := "1.0-SNAPSHOT"

scalaVersion := "2.9.1"

sbtVersion := "0.11.3"

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.0.4",
  // "org.scalatra" %% "scalatra-scalate" % "2.0.4",
  // "org.scalatra" %% "scalatra-specs2" % "2.0.4" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "7.6.0.v20120127" % "container",
  "javax.servlet" % "servlet-api" % "2.5" % "provided"
)

libraryDependencies += "org.specs2" %% "specs2" % "1.12.1" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

// Possibly need to move to more scala-ish test framework
libraryDependencies += "com.novocode" % "junit-interface" % "0.8" % "test->default"

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                  "releases"  at "http://oss.sonatype.org/content/repositories/releases")