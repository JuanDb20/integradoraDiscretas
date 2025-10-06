package logic

import scala.annotation.tailrec

class Kadane{
// helper functions for list handling
  @tailrec
  final def len(xs: List[Int], acc: Int = 0): Int =
    xs match
      case Nil     => acc
      case _ :: ys => len(ys, acc + 1)

  @tailrec
  final def lenMatrix(xs: List[List[Int]], acc: Int = 0): Int =
    xs match
      case Nil     => acc
      case _ :: ys => lenMatrix(ys, acc + 1)

  def at(xs: List[Int], i: Int): Int =
    (xs, i) match
      case (Nil, _)      => 0
      case (h :: _, 0)   => h
      case (_ :: t, n)   => at(t, n - 1)

  def matrixAt(matrix: List[List[Int]], i: Int): List[Int] =
    (matrix, i) match
      case (Nil, _)       => Nil
      case (row :: _, 0)  => row
      case (_ :: rest, n) => matrixAt(rest, n - 1)

  @tailrec
  final def invertList(xs: List[Int], acc: List[Int] = Nil): List[Int] =
    xs match
      case Nil    => acc
      case h :: t => invertList(t, h :: acc)

  @tailrec
  final def sumLists(a: List[Int], b: List[Int], acc: List[Int] = Nil): List[Int] =
    (a, b) match
      case (Nil, Nil)         => invertList(acc)
      case (x :: xs, Nil)     => sumLists(xs, Nil, x :: acc)
      case (Nil, y :: ys)     => sumLists(Nil, ys, y :: acc)
      case (x :: xs, y :: ys) => sumLists(xs, ys, (x + y) :: acc)

  def zeros(n: Int): List[Int] =
    if n <= 0 then Nil else 0 :: zeros(n - 1)
// auxiliary functions of minimums

  @tailrec
  final def minInList(xs: List[Int], current: Int): Int =
    xs match
      case Nil    => current
      case h :: t => if h < current then minInList(t, h) else minInList(t, current)

  def min2(a: Int, b: Int): Int = if a <= b then a else b

  def minInMatrix(matrix: List[List[Int]]): Int =
    matrix match
      case Nil        => 0
      case row :: Nil => minInList(row, at(row, 0))
      case row :: rest => min2(minInList(row, at(row, 0)), minInMatrix(rest))


// this kadane is a 1d kadane
  def kadane(arr: List[Int]): Int =
    arr match
      case Nil    => 0
      case h :: t => kadaneLoop(t, h, h)

  @tailrec
  final def kadaneLoop(xs: List[Int], current: Int, best: Int): Int =
    xs match
      case Nil => best
      case y :: ys =>
        if y > current + y then
          if y > best then kadaneLoop(ys, y, y)
          else kadaneLoop(ys, y, best)
        else
          if current + y > best then kadaneLoop(ys, current + y, current + y)
          else kadaneLoop(ys, current + y, best)
// main function _=)
  def maxSubmatrix(matrix: List[List[Int]]): Int =
    matrix match
      case Nil => 0
      case _   => maxSubRec(matrix, 0, lenMatrix(matrix) - 1)

  def maxSubRec(matrix: List[List[Int]], top: Int, bottom: Int): Int =
    if top == bottom then kadane(matrixAt(matrix, top))
    else
      maxOfThree(
        maxSubRec(matrix, top, (top + bottom) / 2),
        maxSubRec(matrix, (top + bottom) / 2 + 1, bottom),
        maxCrossing(matrix, top, (top + bottom) / 2, bottom)
      )

// crossover in the middle row
  def maxCrossing(matrix: List[List[Int]], top: Int, mid: Int, bottom: Int): Int =
    loopTop(matrix, mid, top, mid, bottom, zeros(len(matrixAt(matrix, 0))), minInMatrix(matrix))
// loopTop: loops upwards accumulating accUp;
// for each accUp, call loopBottom to combine with bottoms
  @tailrec
  final def loopTop(matrix: List[List[Int]], row: Int, top: Int, mid: Int, bottom: Int, accUp: List[Int], bestSoFar: Int): Int =
    if row < top then bestSoFar
    else
      loopTop(
        matrix,
        row - 1,
        top,
        mid,
        bottom,
        sumLists(accUp, matrixAt(matrix, row)),
        max2(
          bestSoFar,
          loopBottom(
            matrix,
            mid + 1,
            bottom,
            zeros(len(matrixAt(matrix, 0))),
            minInMatrix(matrix),
            sumLists(accUp, matrixAt(matrix, row))
          )
        )
      )

// loopBottom: loops down accumulating accDown,
// calculating kadane(sumLists(accUp, accDown)) at each step
  @tailrec
  final def loopBottom(matrix: List[List[Int]], rowB: Int, bottom: Int, accDown: List[Int], bestSoFar: Int, accUp: List[Int]): Int =
    if rowB > bottom then bestSoFar
    else
      loopBottom(
        matrix,
        rowB + 1,
        bottom,
        sumLists(accDown, matrixAt(matrix, rowB)),
        max2(
          bestSoFar,
          kadane(sumLists(accUp, sumLists(accDown, matrixAt(matrix, rowB))))
        ),
        accUp
      )

  //auxiliaritos
  def maxOfThree(a: Int, b: Int, c: Int): Int =
    if a >= b && a >= c then a
    else if b >= a && b >= c then b
    else c

  def max2(a: Int, b: Int): Int = if a >= b then a else b
  }