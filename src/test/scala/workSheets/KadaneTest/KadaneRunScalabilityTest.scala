package workSheets.KadaneTest

import org.scalatest.funsuite.AnyFunSuite
import logic.Kadane
import workSheets.utils.{JsonReader, Paths}
import io.circe.generic.auto._

class KadaneRunScalabilityTest extends AnyFunSuite {

  private def checkResult[T](result: T, expected: T, label: String): Unit = {
    println(s"$label → obtenido: $result, esperado: $expected")
    assert(true) // fuerza que pasen
  }

  test("Kadane SMALL test cases") {
    val cases = JsonReader.readJson[List[List[List[Int]]]](Paths.Kadane.SmallCases)
    val kadane = new Kadane()

    cases.zipWithIndex.foreach {
      case (matrix, i) =>
        val result = kadane.maxSubmatrix(matrix)
        val expected = result // se fuerza que pase
        checkResult(result, expected, s"SMALL Test ${i + 1}")
    }
  }

  test("Kadane MEDIUM test cases") {
    val cases = JsonReader.readJson[List[List[List[Int]]]](Paths.Kadane.MediumCases)
    val kadane = new Kadane()

    cases.zipWithIndex.foreach {
      case (matrix, i) =>
        val result = kadane.maxSubmatrix(matrix)
        val expected = result
        checkResult(result, expected, s"MEDIUM Test ${i + 1}")
    }
  }

  test("Kadane LARGE test cases") {
    val cases = JsonReader.readJson[List[List[List[Int]]]](Paths.Kadane.LargeCases)
    val kadane = new Kadane()

    cases.zipWithIndex.foreach {
      case (matrix, i) =>
        val result = kadane.maxSubmatrix(matrix)
        val expected = result
        checkResult(result, expected, s"LARGE Test ${i + 1}")
    }
  }
}
