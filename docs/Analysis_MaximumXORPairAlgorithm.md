# Analysis of the Maximum XOR Pair Algorithm

The **Maximum XOR Pair algorithm** uses a **Divide and Conquer** approach based on the binary representation of integers to efficiently find the pair of numbers that maximizes the XOR operation in a list.

## 1. Input and Output

**Input:**  
A list of non-negative integers.

Example:

```scala
nums = List(3, 10, 5, 25, 2, 8)
```

**Output:**  
A tuple `(maxXor, (x, y))` where `maxXor` is the maximum XOR value and `(x, y)` is the pair that produces it.

```scala
result = (28, (5, 25))
```

## 2. General Idea (Divide and Conquer)

The algorithm exploits the binary representation of numbers, processing them bit by bit from the most significant bit (MSB) to the least significant bit (LSB).

At each bit position, the list is divided into two partitions:
- Numbers with bit 0 at position `b`
- Numbers with bit 1 at position `b`

$$
\text{Left partition: } bit_b = 0, \quad \text{Right partition: } bit_b = 1
$$

For each recursive level, the algorithm computes:
1. The maximum XOR **entirely within the left partition**
2. The maximum XOR **entirely within the right partition**
3. The maximum XOR **crossing both partitions**

The final result is the maximum among these three cases.

The key insight is that to maximize XOR, we prefer pairs where bits differ at the most significant positions. By dividing the problem based on bit values, we can efficiently explore pairs that differ at high-order bits first.

## 3. Proof of Correctness (Structural Induction)

### a. Base Case

When the bit position is less than 0 (all bits have been processed), the algorithm returns the XOR of the first two elements in the list.

For a list with at least two elements:

$$
maxXorPair(nums, -1) = (nums[0] \oplus nums[1], (nums[0], nums[1]))
$$

This is correct since we have exhausted all bit positions and must choose from the remaining elements.

When the list has fewer than two elements, the algorithm returns `(0, (0, 0))`, which is correct as no valid pair exists.

$$
T(1) = \Theta(1)
$$

### b. Inductive Hypothesis

Assume the algorithm correctly computes the maximum XOR pair for all sublists at bit position `b-1`.

That is, for any partition P:

$$
maxXorPairDC(P, b-1) \text{ returns the correct maximum XOR pair for partition } P
$$

### c. Inductive Step

For a list at bit position `b`, the algorithm:

1. Divides the list into two partitions based on bit `b`:
   $$
   left = \{x \in nums \mid bit_b(x) = 0\}, \quad right = \{x \in nums \mid bit_b(x) = 1\}
   $$

2. Recursively computes:
    - `leftMax = maxXorPairDC(left, b-1)`: Maximum XOR within left partition
    - `rightMax = maxXorPairDC(right, b-1)`: Maximum XOR within right partition
    - `crossMax = crossMax(left, right, b-1)`: Maximum XOR across partitions

3. For `crossMax`, the algorithm explores four cases when both partitions are non-empty:
    - Pairs from `left[bit=0]` and `right[bit=1]`: contributes `1` at bit position `b`
    - Pairs from `left[bit=1]` and `right[bit=0]`: contributes `1` at bit position `b`
    - Pairs from `left[bit=0]` and `right[bit=0]`: contributes `0` at bit position `b`
    - Pairs from `left[bit=1]` and `right[bit=1]`: contributes `0` at bit position `b`

   The algorithm prioritizes cases where bits differ (contributing 1) and adds `2^b` to the result when a cross pair is found.

4. Returns:
   $$
   \max(leftMax, rightMax, crossMax)
   $$

By the inductive hypothesis, all recursive calls return correct results. The combination step correctly evaluates all possible pairs by considering:
- Pairs entirely within each partition (handled recursively)
- Pairs crossing partitions (handled by crossMax)

Since every pair in the original list falls into one of these categories, and each is correctly evaluated, the algorithm returns the correct maximum XOR pair.

**Conclusion:** The algorithm is correct by structural induction.

## 4. Recurrence Relation

At each recursion level for a list of size `n` at bit position `b`:
- Split operation: O(n) to partition the list
- 2 recursive calls on partitions of average size n/2
- Cross computation: O(n^2) in the worst case for evaluating all cross pairs

However, the cross computation is also recursive and follows the same pattern, leading to:

$$
T(n, b) = 2T(n/2, b-1) + O(n \cdot b)
$$

Since we process `b = 32` bits (for 32-bit integers):

$$
T(n) = O(n \log C)
$$

where C is the maximum value (2^31 for 32-bit integers), thus log C = 32.

## 5. Master Theorem Parameters

For the primary recurrence focusing on list size:

| Parameter | Value | Explanation |
|-----------|-------|-------------|
| **a** | 2 | Two recursive calls (left and right partitions) |
| **b** | 2 | Each partition has approximately half the elements |
| **f(n)** | O(n) | Cost of splitting and processing at each bit level |

## 6. Critical Term

$$
n^{\log_b a} = n^{\log_2 2} = n
$$

## 7. Comparing f(n) and n^{log_b a}

$$
f(n) = O(n) \quad \text{and} \quad n^{\log_b a} = \Theta(n)
$$

Thus:

$$
f(n) = \Theta(n^{\log_b a})
$$

This corresponds to **Case 2** of the Master Theorem.

## 8. Applying the Master Theorem

By **Case 2** of the Master Theorem:

$$
T(n) = \Theta(n \log n)
$$

However, considering the bit-processing dimension:

$$
T(n) = O(n \log C)
$$

where C = 2^31 for 32-bit integers, making log C = 32 (constant).

## 9. Final Complexity

**Asymptotic complexity:**

$$
T(n) = O(n \log C) = O(32n) = O(n)
$$

For practical purposes with fixed-width integers (32 bits):

$$
T(n) = O(n)
$$

## 10. Comparison with Other Approaches

| Algorithm | Strategy | Complexity | Observation |
|-----------|----------|------------|-------------|
| Brute Force | Compare all pairs | O(n^2) | Inefficient for large n |
| Trie-based | Build binary trie | O(n log C) | Space-intensive |
| Divide and Conquer (ours) | Recursive bit partitioning | O(n log C) | Space-efficient, purely functional |

## 11. Practical Interpretation

At each bit level, we partition numbers based on whether they have 0 or 1 at that position. This allows us to:
- Quickly identify pairs that differ at high-order bits (maximizing XOR)
- Recursively solve smaller subproblems
- Efficiently combine results from different partitions

The algorithm prioritizes finding differences in the most significant bits first, which naturally leads to maximum XOR values.

## 12. Summary of Analysis

| Step | Description |
|------|-------------|
| 1 | Defined recurrence: T(n) = 2T(n/2) + O(n) per bit level |
| 2 | Identified parameters: a = 2, b = 2, f(n) = O(n) |
| 3 | Compared f(n) and n^{log_b a} = n |
| 4 | Applied Master Theorem (Case 2) |
| 5 | Adjusted for bit-processing: T(n) = O(n log C) |

## 13. Intuitive Summary

- Each division splits numbers by a single bit value
- Numbers with different bits at position b can produce larger XOR values
- Recursive processing ensures all significant bit positions are considered
- The algorithm efficiently prunes the search space by focusing on bit-level differences

**Final Result:**

$$
T(n) = O(n \log C)
$$

For 32-bit integers where log C = 32 (constant):

$$
T(n) = O(n)
$$