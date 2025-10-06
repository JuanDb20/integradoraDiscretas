package workSheets.utils

import io.circe._
import io.circe.parser._
import scala.io.Source

object JsonReader {

  def readJson[T: Decoder](path: String): T = {
    val src = Source.fromResource(path)
    try {
      val jsonString = src.mkString
      decode[T](jsonString) match {
        case Right(value) => value
        case Left(err)    => throw new Exception(s"Error parsing JSON ($path): ${err.getMessage}")
      }
    } finally {
      src.close()
    }
  }
}
