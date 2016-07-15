name := "Aktaion"

version := "1.0"

javaOptions ++= Seq("-Xmx4g", "-XX:MaxPermSize=2056M")

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.Aktaion",
  scalaVersion := "2.11.8",
  test in assembly := {}
)

//lazy val app = (project in file("app")).
//  settings(commonSettings: _*).
//  settings(
//    //  mainClass in assembly := Some("com.Aktaion.Main"),
//    // more settings here ...
//  )


//libraryDependencies ++= Seq("org.scala-lang" % "scala-compiler" % "2.11.8")


//libraryDependencies ++= Seq(
//  "org.scala-lang" % "scala-compiler" % "2.11.8",
//  "org.scala-lang" % "scala-reflect" % "2.11.8"
// // "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4"
//)


//libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.2" % "provided"

//libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.2"
//
//libraryDependencies += "com.databricks" % "spark-csv_2.10" % "1.4.0"
//
libraryDependencies ++=  Seq( ("org.apache.spark" %% "spark-mllib"  % "1.6.2").exclude("javax.xml", "bind").exclude("stax-api", "stax") )


//libraryDependencies +=
//  ("org.apache.spark" %% "spark-mllib" % "1.6.2" excludeAll (
//      ExclusionRule(organization = "javax.xml")
//      )
//    )
libraryDependencies ++= Seq(
  ("org.apache.spark" %% "spark-core" % "1.6.2").
    //    exclude("org.eclipse.jetty.orbit", "javax.servlet").
    //    exclude("org.eclipse.jetty.orbit", "javax.transaction").
    //    exclude("org.eclipse.jetty.orbit", "javax.mail").
    exclude("org.eclipse.jetty.orbit", "javax.activation").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("commons-collections", "commons-collections").
    exclude("com.esotericsoftware.minlog", "minlog")
  // exclude("com.google.guava", "guava")
)

//resolvers += "Cloudera Repos" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
resolvers += "AkkaRepository" at "http://repo.akka.io/releases/"


assemblyMergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) => {
  // case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs@_*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs@_*) => MergeStrategy.last
  case PathList("com", "google", xs@_*) => MergeStrategy.last
  case PathList("javax", "xml", xs@_*) => MergeStrategy.last
  //case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case x => old(x)
}
}



libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

//libraryDependencies += "com.rockymadden.stringmetric" %% "stringmetric-core" % "0.27.4"

lazy val hardClean = taskKey[Unit]("Deletes all the 'target' directories only. It will not clean dependencies and caches.")

hardClean := {
  sbt.IO.delete(file(baseDirectory.value + "/target/"))
  sbt.IO.delete(file(baseDirectory.value + "/project/target"))
  sbt.IO.delete(file(baseDirectory.value + "/project/project/target"))
  clean.value
}

//libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.4.0"

// https://mvnrepository.com/artifact/nz.ac.waikato.cms.weka/weka-stable
libraryDependencies += "nz.ac.waikato.cms.weka" % "weka-stable" % "3.6.6"