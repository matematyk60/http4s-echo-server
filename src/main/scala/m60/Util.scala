package m60

import cats.Show
import cats.effect.IO
import cats.syntax.show.toShow
import fansi.Color._
import pprint.PPrinter

import scala.util.Random

object Util {

  implicit class IOPrettyPrinter(io: IO.type) {
    def pprintln[A](a: A)(implicit S: Show[A] = Show.fromToString[A]): IO[Unit] = {
      val printer = new PPrinter(colorLiteral = colors(Random.nextInt(colors.size)))

      IO(printer.pprintln(a.show))
    }
  }

  private val colors = Vector(
    Red,
    Green,
    Yellow,
    Blue,
    Magenta,
    Cyan,
    LightRed,
    LightGreen,
    LightBlue,
    LightMagenta,
    LightCyan,
  )

}
