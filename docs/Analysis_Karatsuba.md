# Analysis of the Karatsuba Algorithm

The **Karatsuba algorithm** allows multiplying two polynomials (or large integers) in less time than the traditional quadratic algorithm.  
The central idea is to **reduce the number of recursive multiplications from 4 to 3**, using auxiliary additions and subtractions.

---

## 1. Input and Output

**Input:**  
Two lists `a` and `b`, each containing `n` integer coefficients.  
Example:

```scala
a = [2, 1, 1]
b = [2, 1, 1]
```

**Output:**  
A list `c` with the coefficients of the product polynomial:

```scala
c = [4, 4, 5, 2, 1]
```

---

## 2. General Idea (Divide and Conquer)

The algorithm splits each polynomial into two halves:

$[
A(x) = A_1(x) \cdot x^m + A_0(x)
]$
$[
B(x) = B_1(x) \cdot x^m + B_0(x)
]$

where $( m = \lfloor n/2 \rfloor )$.

Then it computes:

$[
Z_0 = A_0 \cdot B_0
]$
$[
Z_2 = A_1 \cdot B_1
]$
$[
Z_1 = (A_0 + A_1)(B_0 + B_1)
]$

and combines the results as:

$[
C(x) = Z_0 + (Z_1 - Z_0 - Z_2)x^m + Z_2x^{2m}
]$

---

## 3. Proof of Correctness (Structural Induction)

### a. Base Case

When \( n = 1 \), the algorithm directly performs:

$[
Karatsuba([a_0], [b_0]) = [a_0 \times b_0]
]$

which is correct since it multiplies the only coefficients available.

---

### b. Inductive Hypothesis

Assume that the **Karatsuba** algorithm correctly multiplies any pair of lists of size ≤ \( n/2 \).  
That is:

$[
Karatsuba(A, B) = A \times B \quad \text{for all } |A|, |B| \le n/2
]$

---

### c. Inductive Step

We want to prove correctness for size \( n \).

The algorithm divides the polynomials:

$[
A(x) = A_1x^m + A_0, \quad B(x) = B_1x^m + B_0
]$

Computes three recursive multiplications:

$[
Z_0 = Karatsuba(A_0, B_0)
]$
$[
Z_2 = Karatsuba(A_1, B_1)
]$
$[
Z_1 = Karatsuba(A_0 + A_1, B_0 + B_1)
]$

and combines the results with:

$[
C(x) = Z_0 + (Z_1 - Z_0 - Z_2)x^m + Z_2x^{2m}
]$

Expanding algebraically:

$[
(A_1x^m + A_0)(B_1x^m + B_0) = A_1B_1x^{2m} + (A_1B_0 + A_0B_1)x^m + A_0B_0
]$

Since by hypothesis the recursive calls produce correct results, the recombination is also correct.

✅ **Conclusion:**  
By structural induction, **Karatsuba returns the correct product of the two polynomials.**

---

##  4. Recurrence Relation

At each recursive call:

- 3 recursive subproblems (Z₀, Z₁, Z₂) of size `n/2`
- Additional operations (additions, subtractions, shifts) with cost Θ(n)

Hence:

$[
T(n) = 3T(n/2) + Θ(n)
]$

with base condition:

$[
T(1) = Θ(1)
]$

---

## 5. Master Theorem Parameters

| Parameter | Value | Explanation |
|------------|--------|-------------|
| **a** | 3 | Three recursive calls per level |
| **b** | 2 | Each subproblem is half the size |
| **f(n)** | Θ(n) | Cost of additions, subtractions, and shifts |

---

## 6. Critical Term

$[
n^{\log_b a} = n^{\log_2 3} ≈ n^{1.585}
]$

---

## 7. Comparing f(n) and n^{log_b a}

We have:

$[
f(n) = Θ(n)
]$
$[
n^{\log_2 3} = Θ(n^{1.585})
]$

Thus:

$[
f(n) = O(n^{\log_2 3 - ε}), \text{ with } ε ≈ 0.585
]$

This corresponds to **Case 1 of the Master Theorem**.

---

## 8. Applying the Master Theorem

By Case 1, we get:

$[
T(n) = Θ(n^{\log_2 3}) ≈ Θ(n^{1.585})
]$

---

## 9. Final Complexity

**Asymptotic complexity:**

$[
T(n) = Θ(n^{1.585})
]$

This is more efficient than the classic multiplication method:

$[
T(n) = Θ(n^2)
]$

---

## 10. Comparison with the Traditional Multiplication

| Algorithm | Recurrence                  | Complexity | Observation |
|------------|-----------------------------|-------------|--------------|
| Classic Multiplication | $( T(n) = 4T(n/2) + Θ(n) )$ | Θ(n²) | 4 multiplications per level |
| Karatsuba | $( T(n) = 3T(n/2) + Θ(n) $) | Θ(n¹·⁵⁸⁵) | 3 multiplications per level |

---

## 11. Base Case (Constant)

When the polynomials are small (n = 1):

$[
T(1) = Θ(1)
]$

This closes the recurrence.

---

## 12. General Summary of the Analysis

| Step | Description                              |
|------|------------------------------------------|
| 1 | Define recurrence $(T(n)=3T(n/2)+Θ(n))$  |
| 2 | Identify parameters: a=3, b=2, f(n)=Θ(n) |
| 3 | Compute critical term $(n^{\log_2 3})$   |
| 4 | Compare f(n) → Case 1 of Master Theorem  |
| 5 | Result: $(T(n)=Θ(n^{1.585}))$            |

---

## 13. Practical Interpretation

Each time the problem is divided, **Karatsuba reduces 4 multiplications to 3**, compensating for the additional cost of additions and subtractions.  
This improves efficiency when `n` is large (polynomials or large numbers).

Your Scala implementation follows this structure exactly:
- 3 recursive calls (`z0`, `z1`, `z2`)
- Linear additions and subtractions (`sumar`, `restar`)
- No explicit loops; fully recursive and pure

---

## 14. Final Result

$[
T(n) = Θ(n^{\log_2 3}) ≈ Θ(n^{1.585})
]$

The **Karatsuba algorithm** is asymptotically **faster** than the classical multiplication method $( Θ(n^2) )$.

---
