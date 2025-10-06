package logic

import scala.annotation.tailrec

class Karatsuba:

  // Custom function to compute the length of a list
  @tailrec
  final def listLength(lst: List[Int], count: Int = 0): Int = lst match
    case Nil      => count
    case _ :: xs  => listLength(xs, count + 1)

  // Pads a list with zeros until its length equals n
  def padWithZeros(list: List[Int], n: Int): List[Int] =
    val diff = n - listLength(list)
    if diff <= 0 then list
    else appendZeros(list, diff)

  // Helper to append k zeros to a list
  @tailrec
  final def appendZeros(list: List[Int], k: Int): List[Int] =
    if k == 0 then list else appendZeros(list :+ 0, k - 1)

  // Auxiliary tail-recursive function for addition
  @tailrec
  final def addAux(a: List[Int], b: List[Int], acc: List[Int] = Nil): List[Int] =
    (a, b) match
      case (Nil, Nil)         => reverse(acc)
      case (x :: xs, Nil)     => addAux(xs, Nil, x :: acc)
      case (Nil, y :: ys)     => addAux(Nil, ys, y :: acc)
      case (x :: xs, y :: ys) => addAux(xs, ys, (x + y) :: acc)

  // Adds two polynomials
  def add(a: List[Int], b: List[Int]): List[Int] =
    val size = max(listLength(a), listLength(b))
    addAux(padWithZeros(a, size), padWithZeros(b, size))

  // Auxiliary tail-recursive function for subtraction
  @tailrec
  final def subtractAux(a: List[Int], b: List[Int], acc: List[Int] = Nil): List[Int] =
    (a, b) match
      case (Nil, Nil)         => reverse(acc)
      case (x :: xs, Nil)     => subtractAux(xs, Nil, x :: acc)
      case (Nil, y :: ys)     => subtractAux(Nil, ys, (-y) :: acc)
      case (x :: xs, y :: ys) => subtractAux(xs, ys, (x - y) :: acc)

  // Subtracts two polynomials
  def subtract(a: List[Int], b: List[Int]): List[Int] =
    val size = max(listLength(a), listLength(b))
    subtractAux(padWithZeros(a, size), padWithZeros(b, size))

  // Shifts a polynomial to the right (multiplies by x^k)
  def shift(a: List[Int], k: Int): List[Int] =
    prependZeros(a, k)

  // Helper to prepend zeros
  @tailrec
  final def prependZeros(a: List[Int], k: Int): List[Int] =
    if k == 0 then a else prependZeros(0 :: a, k - 1)

  // Custom max function (no math.max)
  def max(a: Int, b: Int): Int = if a > b then a else b

  // Custom reverse function
  @tailrec
  final def reverse(lst: List[Int], acc: List[Int] = Nil): List[Int] = lst match
    case Nil      => acc
    case x :: xs  => reverse(xs, x :: acc)

  // Custom take
  def take(lst: List[Int], n: Int): List[Int] = lst match
    case Nil => Nil
    case _ if n <= 0 => Nil
    case x :: xs => x :: take(xs, n - 1)

  // Custom drop
  def drop(lst: List[Int], n: Int): List[Int] = lst match
    case Nil => Nil
    case _ if n <= 0 => lst
    case _ :: xs => drop(xs, n - 1)

  // Recursive Karatsuba algorithm
  def karatsuba(a: List[Int], b: List[Int]): List[Int] =
    val n = max(listLength(a), listLength(b))
    val size = if n == 0 then 1 else n
    val pa = padWithZeros(a, size)
    val pb = padWithZeros(b, size)

    if size == 1 then List(pa.head * pb.head)
    else
      val m = size / 2
      val aLow  = take(pa, m)
      val aHigh = drop(pa, m)
      val bLow  = take(pb, m)
      val bHigh = drop(pb, m)

      val z0 = karatsuba(aLow, bLow)
      val z2 = karatsuba(aHigh, bHigh)
      val sumA = add(aLow, aHigh)
      val sumB = add(bLow, bHigh)
      val z1 = karatsuba(sumA, sumB)

      val middle = subtract(subtract(z1, z0), z2)

      val part1 = z0
      val part2 = shift(middle, m)
      val part3 = shift(z2, 2 * m)

      add(add(part1, part2), part3)

  // Removes trailing zeros from a polynomial
  @tailrec
  final def clean(lst: List[Int], acc: List[Int] = Nil): List[Int] = lst match
    case Nil => if acc == Nil then List(0) else reverse(acc)
    case x :: xs =>
      if xs == Nil && x == 0 && acc != Nil then reverse(acc)
      else clean(xs, x :: acc)

  // Public multiply function
  def multiply(a: List[Int], b: List[Int]): List[Int] =
    clean(karatsuba(a, b))
