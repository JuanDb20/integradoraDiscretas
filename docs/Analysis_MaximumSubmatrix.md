
# Analysis of the Maximum Submatrix Algorithm

The **Maximum Submatrix Sum algorithm** uses a hybrid approach combining **Divide and Conquer** with the **1D logic.Kadane algorithm** to find the maximum sum of any contiguous submatrix within a 2D integer matrix.


## 1. Input and Output

**Input:**  
A 2D matrix $matrix$ of size $n \times m$ containing integer values.

Example:

```scala
matrix = 
  [1, 2, -1],
  [-3, 4, 5],
  [2, -1, 2]
```

**Output:**  
An integer representing the maximum possible sum of a contiguous submatrix.

```scala
result = 10
```



## 2. General Idea (Divide and Conquer + logic.Kadane)

The algorithm recursively divides the matrix into two halves by rows:

$$
\text{Top half: } [0..mid], \quad \text{Bottom half: } [mid+1..n-1]
$$

For each recursive level, it computes:
1. The maximum submatrix sum **entirely in the top half**.
2. The maximum submatrix sum **entirely in the bottom half**.
3. The maximum submatrix sum that **crosses the dividing line** (computed using logic.Kadane 1D).

The final result is the maximum among these three cases.



## 3. Proof of Correctness (Structural Induction)

### a. Base Case

When the matrix contains a **single row**, the problem reduces to the 1D case.  
We apply **logic.Kadane’s algorithm**, which correctly computes the maximum sum of a contiguous subarray in $ O(m)$.

$$
T(1) = Θ(m)
$$

### b. Inductive Hypothesis

Assume the algorithm correctly computes the maximum submatrix sum for all matrices up to $n/2 $ rows.

That is:

$$
\text{maxSubRec}(matrix, 0, n/2 - 1) \text{ and } \text{maxSubRec}(matrix, n/2, n - 1)
$$

return the correct maximum submatrix sums.

### c. Inductive Step

For a matrix of  $n$ rows:

1. Divide the matrix into two halves:
   $$
   top = [0..mid], \quad bottom = [mid+1..n-1]
   $$

2. Recursively compute:
   - $topSum = T(n/2)$
   - $bottomSum = T(n/2)$
   - $crossSum = f(n)$, computed by evaluating all pairs of rows crossing the midline and applying logic.Kadane to their column sums.

3. Combine results:
   $$
   \text{maxSum} = \max(topSum, bottomSum, crossSum)
   $$

Since each recursive call and combination step is computed correctly, by structural induction, the algorithm returns the correct maximum submatrix sum.

**Conclusion:** The algorithm is correct by induction.


## 4. Recurrence Relation

At each recursion level, we perform:
- 2 recursive calls (top and bottom halves)
- A combination step $f(n)$ that processes $O(n^2 m)$ operations.

Hence:

$$
T(n) = 2T(n/2) + Θ(n^2 m)
$$



## 5. Master Theorem Parameters

| Parameter | Value | Explanation |
|------------|--------|-------------|
| **a** | 2 | Two recursive calls (top and bottom halves) |
| **b** | 2 | Each call processes half the rows |
| **f(n)** | Θ(n²·m) | Cost of combining halves using logic.Kadane on all row pairs |



## 6. Critical Term

$$
n^{\log_b a} = n^{\log_2 2} = n
$$



## 7. Comparing \( f(n) \) and \( n^{\log_b a} \)

$$
f(n) = Θ(n^2 m) \quad \text{and} \quad n^{\log_b a} = Θ(n)
$$

Thus:

$$
f(n) = Ω(n^{1 + ε}), \text{ with } ε = 1
$$

and \( f(n) \) satisfies the **regularity condition** because:

$$
a \cdot f(n/b) = 2 \cdot Θ((n/2)^2 m) = Θ(n^2 m / 2) = 0.5 f(n)
$$


## 8. Applying the Master Theorem

By **Case 3** of the Master Theorem:

$$
T(n) = Θ(f(n)) = Θ(n^2 m)
$$



## 9. Final Complexity

**Asymptotic complexity:**

$$
T(n) = Θ(n^2 m)
$$

For a square matrix where $n = m$:

$$
T(n) = Θ(n^3)
$$


## 10. Comparison with Other Approaches

| Algorithm | Strategy | Recurrence | Complexity | Observation |
|------------|-----------|-------------|-------------|--------------|
| Brute Force | All submatrices scanned | — | Θ(n⁴) | Extremely slow |
| logic.Kadane 2D (Iterative) | Collapsed row pairs | — | Θ(n²·m) | Same as our final complexity |
| Divide and Conquer (ours) | Recursive + logic.Kadane | $2T(n/2) + Θ(n²·m) $ | Θ(n²·m) | Structured and extensible |

## 11. Practical Interpretation

At every level, we reduce the matrix size by half (Divide), compute the best result for each (Conquer), and then find possible maximum submatrices that **cross the boundary** using logic.Kadane (Combine).

The total work across all levels remains proportional to $ n^2 m $, making the algorithm efficient yet precise.


## 12. Summary of Analysis

| Step | Description |
|------|--------------|
| 1 | Defined recurrence: $T(n) = 2T(n/2) + Θ(n²·m) $|
| 2 | Identified parameters: $a = 2, b = 2, f(n) = Θ(n²·m)$ |
| 3 | Compared $f(n)$ and $ n^{\log_b a} = n$ |
| 4 | Applied Master Theorem (Case 3) |
| 5 | Result: $T(n) = Θ(n²·m)$ |

## 13. Intuitive Summary

- Each division halves the rows.
- Each combination checks all possible top–bottom pairs across the middle.
- logic.Kadane ensures the optimal column combination.
- The recursive tree’s cost per level diminishes geometrically, leaving the dominant cost at the top level.

**Final Result:**

$$
T(n) = Θ(n²·m)
\quad \text{and for square matrices } Θ(n³)
$$
