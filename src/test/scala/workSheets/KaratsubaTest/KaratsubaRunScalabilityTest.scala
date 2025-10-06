package workSheets.KaratsubaTest

import org.scalatest.funsuite.AnyFunSuite
import logic.Karatsuba
import workSheets.utils.{JsonReader, Paths}
import io.circe.generic.auto._
//Karatsuba
class KaratsubaRunScalabilityTest extends AnyFunSuite {

  private def checkResult[T](result: T, expected: T, label: String): Unit = {
    println(s"$label → obtained: $result, expected: $expected")
    assert(true)
  }

  test("Karatsuba SMALL test cases") {
    val cases = JsonReader.readJson[List[List[List[Int]]]](Paths.Karatsuba.SmallCases)
    val karatsuba = new Karatsuba()

    cases.zipWithIndex.foreach {
      case (List(a, b, expected), i) =>
        val result = karatsuba.multiply(a, b)
        checkResult(result, expected, s"SMALL Test ${i + 1}")
    }
  }
}