import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object Builds extends Build {
  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    version := "0.1",
    organization := "net.ironforged",
    scalaVersion := "2.10.2"
  )

  lazy val app = Project(
      "RPNCalculator", 
      file("."), 
      settings = buildSettings ++ assemblySettings
    ) settings(
      // placeholder for more settings
    )
}
