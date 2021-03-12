import Dependencies._
import Generators._

ThisBuild / scalaVersion     := "2.13.4"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val copyNativeLib = taskKey[Unit]("copy native lib to resources")

lazy val root = (project in file("."))
  .settings(
    name := "sparse4s",
    copyNativeLib := {
      copyNativeLibToResources((Compile / resourceManaged).value)
    },
    libraryDependencies ++= Seq(
      scalaTest % Test
    ),

    Compile / sourceGenerators += Def.task{
      generateMeta((Compile / sourceManaged).value)
    }.taskValue
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
