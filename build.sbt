name := "Aktaion"

version := "0.1"

javaOptions ++= Seq("-Xmx4g", "-XX:MaxPermSize=2056M")

scalaVersion in ThisBuild := "2.11.8"

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.Aktaion",
  scalaVersion := "2.11.8",
  test in assembly := {}
)

mainClass in assembly := Some("com.aktaion.shell.UserInteractionLogic")

//resolvers += "Cloudera Repos" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
resolvers += "AkkaRepository" at "http://repo.akka.io/releases/"


//lazy val app = (project in file("app")).
//  settings(commonSettings: _*).
//  settings(
//    //  mainClass in assembly := Some("com.Aktaion.Main"),
//    // more settings here ...
//  )

//libraryDependencies ++= Seq("org.scala-lang" % "scala-compiler" % "2.11.8")

//libraryDependencies += "ch.qos.logback" %  "logback-classic" % "1.1.7"

//libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"


libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-compiler" % "2.11.8",
  "org.scala-lang" % "scala-reflect" % "2.11.8"
)


libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.10",
  "org.slf4j" % "slf4j-simple" % "1.7.10"
)

//libraryDependencies += "jline" % "jline" % "0.9.94"

libraryDependencies ++= Seq(
  // ("org.apache.spark" %% "spark-mllib"  % "1.6.2").exclude("javax.xml", "bind").exclude("stax-api", "stax"),
  "jline" % "jline" % "0.9.94",
  "com.rockymadden.stringmetric" %% "stringmetric-core" % "0.27.4",
  "org.apache.spark" %% "spark-core" % "1.6.2" % "provided",
  "org.apache.commons" % "commons-math3" % "3.6.1",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "nz.ac.waikato.cms.weka" % "weka-stable" % "3.6.6"

//  // Last stable release
//  "org.scalanlp" %% "breeze" % "0.12",
//
//  // Native libraries are not included by default. add this if you want them (as of 0.7)
//  // Native libraries greatly improve performance, but increase jar sizes.
//  // It also packages various blas implementations, which have licenses that may or may not
//  // be compatible with the Apache License. No GPL code, as best I know.
//  "org.scalanlp" %% "breeze-natives" % "0.12",
//
//  // The visualization library is distributed separately as well.
//  // It depends on LGPL code
//  "org.scalanlp" %% "breeze-viz" % "0.12",
//
//  "org.scalanlp" %% "english"  % "2015.1.25"

).map(_.exclude("org.slf4j", "slf4j-simple")).map(_.exclude("org.slf4j", "sl4j-log4j12"))

//resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

//libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"

//libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"

//libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

//libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.4.0"

// https://mvnrepository.com/artifact/nz.ac.waikato.cms.weka/weka-stable
//libraryDependencies += "nz.ac.waikato.cms.weka" % "weka-stable" % "3.6.6"


//libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.2" % "provided"

//libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.2"
//
//libraryDependencies += "com.databricks" % "spark-csv_2.10" % "1.4.0"
//

//libraryDependencies += "com.rockymadden.stringmetric" %% "stringmetric-core" % "0.27.4"

//libraryDependencies +=
//  ("org.apache.spark" %% "spark-mllib" % "1.6.2" excludeAll (
//      ExclusionRule(organization = "javax.xml")
//      )
//    )
//libraryDependencies ++= Seq(
//  ("org.apache.spark" %% "spark-core" % "1.6.2").
//    //    exclude("org.eclipse.jetty.orbit", "javax.servlet").
//    //    exclude("org.eclipse.jetty.orbit", "javax.transaction").
//    //    exclude("org.eclipse.jetty.orbit", "javax.mail").
//    exclude("org.eclipse.jetty.orbit", "javax.activation").
//    exclude("commons-beanutils", "commons-beanutils-core").
//    exclude("commons-collections", "commons-collections").
//    exclude("commons-collections", "commons-collections").
//    exclude("com.esotericsoftware.minlog", "minlog").
//    exclude("org.slf4j", "slf4j-simple")
//  // exclude("com.google.guava", "guava")
//)


assemblyMergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) => {
  // case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs@_*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs@_*) => MergeStrategy.last
  case PathList("com", "google", xs@_*) => MergeStrategy.last
  case PathList("javax", "xml", xs@_*) => MergeStrategy.last
  case PathList("org", "slf4j", xs@_*) => MergeStrategy.last
  case PathList("net", "sourceforge", xs@_*) => MergeStrategy.last
  //case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case x => old(x)
}
}
