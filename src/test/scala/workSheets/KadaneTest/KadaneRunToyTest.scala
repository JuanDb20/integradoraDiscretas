package Unit

import logic.Kadane

@main def KadaneRunToyTests(): Unit =
  val k = new Kadane()

  println("==  Toy Tests for the Kadane Algorithm and Auxiliary Functions ==")

  // 1. Tests for len
  println("\n[1] Tests for len:")
  println(k.len(List(1, 2, 3)))     // Expected: 3
  println(k.len(Nil))               // Expected: 0
  println(k.len(List(5)))           // Expected: 1

  // 2. Tests for lenMatrix
  println("\n[2] Tests for lenMatrix:")
  println(k.lenMatrix(List(List(1,2), List(3,4), List(5,6)))) // Expected: 3
  println(k.lenMatrix(Nil))                                  // Expected: 0

  // 3. Tests for at
  println("\n[3] Tests for at:")
  println(k.at(List(10, 20, 30), 1)) // Expected: 20
  println(k.at(List(5), 0))          // Expected: 5
  println(k.at(Nil, 2))              // Expected: 0

  // 4. Tests for matrixAt
  println("\n[4] Tests for matrixAt:")
  println(k.matrixAt(List(List(1,2), List(3,4), List(5,6)), 1)) // Expected: List(3,4)
  println(k.matrixAt(Nil, 0))                                 // Expected: Nil

  // 5. Tests for invertList
  println("\n[5] Tests for invertList:")
  println(k.invertList(List(1, 2, 3))) // Expected: List(3,2,1)
  println(k.invertList(Nil))           // Expected: Nil

  // 6. Tests for sumLists
  println("\n[6] Tests for sumLists:")
  println(k.sumLists(List(1,2,3), List(4,5,6))) // Expected: List(5,7,9)
  println(k.sumLists(List(1,2), List(3)))       // Expected: List(4,2)
  println(k.sumLists(Nil, List(2,2)))          // Expected: List(2,2)

  // 7. Tests for zeros
  println("\n[7] Tests for zeros:")
  println(k.zeros(3)) // Expected: List(0,0,0)
  println(k.zeros(0)) // Expected: Nil

  // 8. Tests for minInList
  println("\n[8] Tests for minInList:")
  println(k.minInList(List(5,2,8,1), 5)) // Expected: 1
  println(k.minInList(List(3,4,2), 3))  // Expected: 2

  // 9. Tests for minInMatrix
  println("\n[9] Tests for minInMatrix:")
  println(k.minInMatrix(List(List(1,2), List(3,0), List(4,5)))) // Expected: 0
  println(k.minInMatrix(Nil))                                  // Expected: 0

  // 10. Tests for kadane (1D)
  println("\n[10] Tests for kadane (1D):")
  println(k.kadane(List(1, -2, 3, 4, -1, 2, 1, -5, 4))) // Expected: 9
  println(k.kadane(List(-1, -2, -3)))                   // Expected: -1
  println(k.kadane(Nil))                                // Expected: 0

  // 11. Tests for maxSubmatrix (2D)
  println("\n[11] Tests for maxSubmatrix (2D):")
  val matrix1 = List(
    List(1, -2, -1, 4),
    List(-8, 3, 4, 2),
    List(3, 8, 10, -8),
    List(-4, -1, 1, 7)
  )
  println(k.maxSubmatrix(matrix1)) // Expected: some positive value, around 27 (depending on your implementation)

  val matrix2 = List(
    List(-1, -2),
    List(-3, -4)
  )
  println(k.maxSubmatrix(matrix2)) // Expected: -1

  println("\nAll Toy Tests executed successfully.")
