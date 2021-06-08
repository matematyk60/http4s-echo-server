version := "0.1"

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

val baseSettings = Seq(
  name := "http4s-echo-server",
  scalaVersion := "2.13.6",
  resolvers ++= Dependencies.additionalResolvers,
  libraryDependencies ++= Dependencies.all,
  parallelExecution in Test := false,
  scalacOptions ++= CompilerOpts.scalacOptions
)

lazy val `http4s-echo-server` =
  project
    .in(file("."))
    .settings(baseSettings: _*)
