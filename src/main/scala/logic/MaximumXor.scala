package logic
import scala.annotation.tailrec

object XorLogic {
  def getBit(n: Int, pos: Int): Int = (n >> pos) & 1

  @tailrec
  def splitByBit(
                  toSplit: List[Int],
                  bit: Int,
                  left: List[Int],
                  right: List[Int]
                ): (List[Int], List[Int]) =
    toSplit match
      case Nil => (left, right)
      case h :: t =>
        if (getBit(h, bit) == 0) splitByBit(t, bit, h :: left, right)
        else splitByBit(t, bit, left, h :: right)

  def splitByBit(xs: List[Int], bit: Int): (List[Int], List[Int]) =
    splitByBit(xs, bit, Nil, Nil)

  def maxTuple(a: (Int, (Int, Int)), b: (Int, (Int, Int))): (Int, (Int, Int)) =
    if (a._1 >= b._1) a else b

  def maxOfThree(
                  a: (Int, (Int, Int)),
                  b: (Int, (Int, Int)),
                  c: (Int, (Int, Int))
                ): (Int, (Int, Int)) =
    maxTuple(maxTuple(a, b), c)

  def maxOfFour(
                 a: (Int, (Int, Int)),
                 b: (Int, (Int, Int)),
                 c: (Int, (Int, Int)),
                 d: (Int, (Int, Int))
               ): (Int, (Int, Int)) =
    maxTuple(maxTuple(a, b), maxTuple(c, d))

  def crossMax(left: List[Int], right: List[Int], bit: Int): (Int, (Int, Int)) =
    if (left == Nil || right == Nil) (-1, (0, 0))
    else if (bit < 0) (left.head ^ right.head, (left.head, right.head))
    else
      crossMaxHelper(
        splitByBit(left, bit),
        splitByBit(right, bit),
        bit,
        left.head,
        right.head
      )

  def crossMaxHelper(
                      leftSplit: (List[Int], List[Int]),
                      rightSplit: (List[Int], List[Int]),
                      bit: Int,
                      leftHead: Int,
                      rightHead: Int
                    ): (Int, (Int, Int)) =
    crossMaxWithSplits(
      leftSplit._1,
      leftSplit._2,
      rightSplit._1,
      rightSplit._2,
      bit,
      leftHead,
      rightHead
    )

  def crossMaxWithSplits(
                          l0: List[Int],
                          l1: List[Int],
                          r0: List[Int],
                          r1: List[Int],
                          bit: Int,
                          leftHead: Int,
                          rightHead: Int
                        ): (Int, (Int, Int)) =
    crossMaxCalcA(
      l0,
      l1,
      r0,
      r1,
      bit,
      leftHead,
      rightHead,
      if (l0 != Nil && r1 != Nil) crossMaxAddBit(crossMax(l0, r1, bit - 1), bit)
      else (-1, (0, 0))
    )

  def crossMaxCalcA(
                     l0: List[Int],
                     l1: List[Int],
                     r0: List[Int],
                     r1: List[Int],
                     bit: Int,
                     leftHead: Int,
                     rightHead: Int,
                     candA: (Int, (Int, Int))
                   ): (Int, (Int, Int)) =
    crossMaxCalcB(
      l0,
      l1,
      r0,
      r1,
      bit,
      leftHead,
      rightHead,
      candA,
      if (l1 != Nil && r0 != Nil) crossMaxAddBit(crossMax(l1, r0, bit - 1), bit)
      else (-1, (0, 0))
    )

  def crossMaxCalcB(
                     l0: List[Int],
                     l1: List[Int],
                     r0: List[Int],
                     r1: List[Int],
                     bit: Int,
                     leftHead: Int,
                     rightHead: Int,
                     candA: (Int, (Int, Int)),
                     candB: (Int, (Int, Int))
                   ): (Int, (Int, Int)) =
    crossMaxCalcC(
      l1,
      r1,
      bit,
      leftHead,
      rightHead,
      candA,
      candB,
      if (l0 != Nil && r0 != Nil) crossMax(l0, r0, bit - 1) else (-1, (0, 0))
    )

  def crossMaxCalcC(
                     l1: List[Int],
                     r1: List[Int],
                     bit: Int,
                     leftHead: Int,
                     rightHead: Int,
                     candA: (Int, (Int, Int)),
                     candB: (Int, (Int, Int)),
                     candC: (Int, (Int, Int))
                   ): (Int, (Int, Int)) =
    crossMaxCalcD(
      bit,
      leftHead,
      rightHead,
      candA,
      candB,
      candC,
      if (l1 != Nil && r1 != Nil) crossMax(l1, r1, bit - 1) else (-1, (0, 0))
    )

  def crossMaxCalcD(
                     bit: Int,
                     leftHead: Int,
                     rightHead: Int,
                     candA: (Int, (Int, Int)),
                     candB: (Int, (Int, Int)),
                     candC: (Int, (Int, Int)),
                     candD: (Int, (Int, Int))
                   ): (Int, (Int, Int)) =
    crossMaxSelectBest(
      leftHead,
      rightHead,
      maxOfFour(candA, candB, candC, candD)
    )

  def crossMaxSelectBest(
                          leftHead: Int,
                          rightHead: Int,
                          best: (Int, (Int, Int))
                        ): (Int, (Int, Int)) =
    if (best._1 == -1) (leftHead ^ rightHead, (leftHead, rightHead)) else best

  def crossMaxAddBit(result: (Int, (Int, Int)), bit: Int): (Int, (Int, Int)) =
    if (result._1 != -1) ((1 << bit) | result._1, result._2) else (0, (0, 0))

  def maxXorPairDC(nums: List[Int], bit: Int): (Int, (Int, Int)) =
    if (nums == Nil || nums.tail == Nil) (0, (0, 0))
    else if (bit < 0) (nums.head ^ nums.tail.head, (nums.head, nums.tail.head))
    else maxXorPairDCWithSplit(splitByBit(nums, bit), bit)

  def maxXorPairDCWithSplit(
                             split: (List[Int], List[Int]),
                             bit: Int
                           ): (Int, (Int, Int)) =
    if (split._1 == Nil) maxXorPairDC(split._2, bit - 1)
    else if (split._2 == Nil) maxXorPairDC(split._1, bit - 1)
    else
      maxXorPairDCCombine(
        split._1,
        split._2,
        bit,
        maxXorPairDC(split._1, bit - 1),
        maxXorPairDC(split._2, bit - 1)
      )

  def maxXorPairDCCombine(
                           left: List[Int],
                           right: List[Int],
                           bit: Int,
                           leftBest: (Int, (Int, Int)),
                           rightBest: (Int, (Int, Int))
                         ): (Int, (Int, Int)) =
    maxOfThree(leftBest, rightBest, crossMax(left, right, bit))

  def maxXorPair(nums: List[Int]): (Int, (Int, Int)) = maxXorPairDC(nums, 31)

}