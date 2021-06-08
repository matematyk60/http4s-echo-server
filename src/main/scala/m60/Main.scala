package m60

import cats.effect.{ExitCode, IO, IOApp}
import io.circe.Json
import io.circe.literal._
import m60.Util._
import org.http4s.HttpRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.circe.{jsonDecoder, jsonEncoder}
import org.http4s.dsl.io._
import org.http4s.server.Router
import org.http4s.syntax.kleisli.http4sKleisliResponseSyntaxOptionT

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {

  val routes: HttpRoutes[IO] = HttpRoutes.of {
    case request =>
      for {
        () <- IO.pprintln("---") *> IO.pprintln(s"Request accepted for path [${request.pathInfo}]")
        body <- request
                 .attemptAs[Json]
                 .map(_.toString())
                 .getOrElseF(request.bodyText.foldMonoid.compile.lastOrError)
        ()           <- IO.pprintln(s"Request headers are [${request.headers}]")
        ()           <- IO.pprintln(s"Request body is [$body]")
        responseBody = json"""
            {
               "name":"John",
               "surname":"Doe",
               "age":23
            }
            """
        response     <- Ok(responseBody)
        ()           <- IO.pprintln(s"Response status is [${response.status}]")
        ()           <- IO.pprintln(s"Response headers are [${response.headers}]")
        ()           <- IO.pprintln(s"Response body is [$responseBody]")
        ()           <- IO.pprintln(s"Request successfully handled") *> IO.pprintln("---")
      } yield response
  }

  private val router = Router(
    "/" -> routes
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO](global)
      .bindHttp(5000, "0.0.0.0")
      .withHttpApp(router)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
