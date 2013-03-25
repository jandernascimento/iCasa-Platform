import sbt._
import sbt.Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "appstore"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
       "mysql" % "mysql-connector-java" % "5.1.18",
        "securesocial" % "securesocial_2.9.1" % "2.0.8"
    )
 // val ssDependencies = Seq(
 //   "com.typesafe" %% "play-plugins-util" % "2.0.3",
 //   "com.typesafe" %% "play-plugins-mailer" % "2.0.4",
 //   "org.mindrot" % "jbcrypt" % "0.3m"
 // )


  //val secureSocial = PlayProject(
  //  "securesocial", appVersion, ssDependencies, mainLang = SCALA, path = file("modules/securesocial")
  //).settings(
  //  resolvers ++= Seq(
  //    "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
  //    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  //  )
  //)

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      templatesImport += "models.values._",
        resolvers += Resolver.url("SecureSocial Repository", url("http://securesocial.ws/repository/snapshots/"))(Resolver.ivyStylePatterns)
    )

}
