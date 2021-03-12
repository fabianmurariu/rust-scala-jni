import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.2"

  def copyNativeLibToResources(resourcesPath: File): Unit = {
    // TODO copy rust lib files here instead of the Makefile
  }
}
