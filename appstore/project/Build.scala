import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "appstore"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
       "mysql" % "mysql-connector-java" % "5.1.18"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      templatesImport += "models.values._"
    )

}
