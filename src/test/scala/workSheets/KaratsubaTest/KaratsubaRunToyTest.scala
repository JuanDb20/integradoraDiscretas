package Unit

import logic.Karatsuba

@main def KaratsubaRunToyTests(): Unit =
  val k = new Karatsuba()

  println("==  Toy Tests for the Karatsuba Algorithm and Auxiliary Functions ==")

  //  1. Tests for padWithZeros
  println("\n[1] Tests for padWithZeros:")
  println(k.padWithZeros(List(1, 2, 3), 5)) // Expected: List(1,2,3,0,0)
  println(k.padWithZeros(List(1, 2, 3), 3)) // Expected: List(1,2,3)
  println(k.padWithZeros(Nil, 3))           // Expected: List(0,0,0)

  //  2. Tests for addAux / add
  println("\n[2] Tests for addAux / add:")
  println(k.add(List(1, 2, 3), List(4, 5, 6))) // Expected: List(5,7,9)
  println(k.add(List(1, 2), List(1)))          // Expected: List(2,2)
  println(k.add(List(), List(5, 6)))           // Expected: List(5,6)

  //  3. Tests for subtractAux / subtract
  println("\n[3] Tests for subtractAux / subtract:")
  println(k.subtract(List(5, 7, 9), List(1, 2, 3))) // Expected: List(4,5,6)
  println(k.subtract(List(2, 2), List(1)))          // Expected: List(1,2)
  println(k.subtract(List(), List(5, 6)))           // Expected: List(-5,-6)

  //  4. Tests for shift
  println("\n[4] Tests for shift:")
  println(k.shift(List(1, 2, 3), 2)) // Expected: List(0,0,1,2,3)
  println(k.shift(List(), 3))        // Expected: List(0,0,0)

  //  5. Tests for clean
  println("\n[5] Tests for clean:")
  println(k.clean(List(1, 2, 3, 0, 0)))  // Expected: List(1,2,3)
  println(k.clean(List(0, 0, 0)))        // Expected: List(0)
  println(k.clean(List(4, 0, 0, 1)))     // Expected: List(4,0,0,1)

  //  6. Tests for karatsuba
  println("\n[6] Tests for karatsuba:")
  println(k.karatsuba(List(1), List(2)))                // Expected: List(2)
  println(k.karatsuba(List(2, 1), List(1, 1)))          // Expected: List(2,3,1)
  println(k.karatsuba(List(2, 1, 1), List(2, 1, 1)))    // Expected: List(4,4,5,2,1)

  //  7. Tests for multiply
  println("\n[7] Tests for multiply (final result):")
  println(k.multiply(List(2, 1, 1), List(2, 1, 1)))  // Expected: List(4,4,5,2,1)
  println(k.multiply(List(1, 0, 1), List(1, 1)))     // Expected: List(1,1,1,1)
  println(k.multiply(List(0), List(5, 3, 2)))        // Expected: List(0)
  println(k.multiply(List(3), List(4)))              // Expected: List(12)

  println("\n All Toy Tests executed successfully.")
