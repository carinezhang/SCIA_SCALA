scalaVersion := "2.11.7"
name := "scala_project"
version := "1.0"

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.3.168",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.slf4j" % "slf4j-simple" % "1.6.4",  
  "org.sorm-framework" % "sorm" % "0.3.18",
)
