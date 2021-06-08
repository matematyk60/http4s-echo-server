import sbt._

object Dependencies {
  private val http4sVersion  = "1.0.0-M23"
  private val circeVersion   = "0.14.1"
  private val logbackVersion = "1.2.3"
  private val pprintVersion  = "0.6.6"

  private val loggingDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion
  )

  private val http4sDependencies = Seq(
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-circe"        % http4sVersion,
    "org.http4s" %% "http4s-dsl"          % http4sVersion,
    "io.circe"   %% "circe-generic"       % circeVersion,
    "io.circe"   %% "circe-literal"       % circeVersion
  )

  private val utilDependencies = Seq(
    "com.lihaoyi" %% "pprint" % pprintVersion
  )

  val all: Seq[ModuleID] =
    loggingDependencies ++
      utilDependencies ++
      http4sDependencies

  val additionalResolvers: Seq[Resolver] = Seq(
    Resolver.jcenterRepo,
    Resolver.mavenCentral,
    Resolver.bintrayRepo("codeheroes", "maven"),
    "Typesafe Repo" at "https://repo.typesafe.com/typesafe/releases/"
  )
}
