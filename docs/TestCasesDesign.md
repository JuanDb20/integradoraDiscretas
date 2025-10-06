# Test Cases Design - integradora Discretas II

## Configuration of Test Scenarios

| Scenario Name | Test Class | Scenario Description |
|--------------|------------|---------------------|
| setupStage1 | KadaneRunToyTest | Empty matrix scenario |
| setupStage2 | KadaneRunToyTest | Single element matrix |
| setupStage3 | KadaneRunToyTest | Small matrix (3x3) with mixed positive and negative values |
| setupStage4 | KadaneRunToyTest | Matrix with all negative values |
| setupStage5 | KadaneRunScalabilityTest | Small test cases (matrices up to 4x6) |
| setupStage6 | KadaneRunScalabilityTest | Medium test cases (matrices 6x6 to 8x8) |
| setupStage7 | KadaneRunScalabilityTest | Large test cases (matrices 10x10 to 12x12) |
| setupStage8 | KaratsubaRunToyTest | Single coefficient polynomials |
| setupStage9 | KaratsubaRunToyTest | Small polynomials (2-4 coefficients) |
| setupStage10 | KaratsubaRunToyTest | Polynomials with zero coefficients |
| setupStage11 | KaratsubaRunScalabilityTest | Small test cases (polynomials up to 4 coefficients) |
| setupStage12 | KaratsubaRunScalabilityTest | Medium test cases (polynomials with 5 coefficients) |
| setupStage13 | KaratsubaRunScalabilityTest | Large test cases (polynomials with 8 coefficients) |
| setupStage14 | XorRunToyTest | Empty list |
| setupStage15 | XorRunToyTest | Single element list |
| setupStage16 | XorRunToyTest | Small lists (2-4 elements) |
| setupStage17 | XorRunScalabilityTest | Small test cases (lists up to 8 elements) |
| setupStage18 | XorRunScalabilityTest | Medium test cases (lists with 10-15 elements) |
| setupStage19 | XorRunScalabilityTest | Large test cases (lists with 20+ elements) |

---

## Test Case Design

### Objective: Verify correctness of Kadane auxiliary functions

| Class | Method | Scenario | Input Values | Expected Result |
|-------|--------|----------|--------------|-----------------|
| Kadane | len | setupStage1 | List(1, 2, 3) | 3 |
| Kadane | len | setupStage1 | Nil | 0 |
| Kadane | len | setupStage2 | List(5) | 1 |
| Kadane | at | setupStage3 | List(10, 20, 30), index=1 | 20 |
| Kadane | at | setupStage1 | Nil, index=2 | 0 |
| Kadane | sumLists | setupStage3 | List(1,2,3), List(4,5,6) | List(5,7,9) |
| Kadane | sumLists | setupStage3 | List(1,2), List(3) | List(4,2) |
| Kadane | minInList | setupStage3 | List(5,2,8,1), current=5 | 1 |
| Kadane | minInMatrix | setupStage3 | [[1,2],[3,0],[4,5]] | 0 |

### Objective: Verify correctness of 1D Kadane algorithm

| Class | Method | Scenario | Input Values | Expected Result |
|-------|--------|----------|--------------|-----------------|
| Kadane | kadane | setupStage3 | List(1, -2, 3, 4, -1, 2, 1, -5, 4) | 9 |
| Kadane | kadane | setupStage4 | List(-1, -2, -3) | -1 |
| Kadane | kadane | setupStage1 | Nil | 0 |
| Kadane | kadane | setupStage2 | List(5) | 5 |
| Kadane | kadane | setupStage3 | List(2, -1, 2, 3, -5, 8) | 8 |

### Objective: Verify correctness of 2D Maximum Submatrix algorithm

| Class | Method | Scenario | Input Values | Expected Result |
|-------|--------|----------|--------------|-----------------|
| Kadane | maxSubmatrix | setupStage3 | [[2, -1, 3], [-2, 4, 1], [1, -1, 2]] | 9 |
| Kadane | maxSubmatrix | setupStage3 | [[1, 2, -1], [-3, 4, 2], [1, -2, 3]] | 7 |
| Kadane | maxSubmatrix | setupStage4 | [[-1, -2, -3], [-4, -5, -6], [-7, -8, -9]] | -1 |
| Kadane | maxSubmatrix | setupStage2 | [[1, -1], [2, 3]] | 5 |
| Kadane | maxSubmatrix | setupStage5 | Small test matrix from JSON | Positive sum value |
| Kadane | maxSubmatrix | setupStage6 | Medium test matrix from JSON | Maximum sum for medium case |
| Kadane | maxSubmatrix | setupStage7 | Large test matrix from JSON | Maximum sum for large case |

### Objective: Verify correctness of Karatsuba auxiliary functions

| Class | Method | Scenario | Input Values | Expected Result |
|-------|--------|----------|--------------|-----------------|
| Karatsuba | listLength | setupStage8 | List(1, 2, 3) | 3 |
| Karatsuba | padWithZeros | setupStage9 | List(1, 2, 3), n=5 | List(1,2,3,0,0) |
| Karatsuba | add | setupStage9 | List(1, 2, 3), List(4, 5, 6) | List(5,7,9) |
| Karatsuba | subtract | setupStage9 | List(5, 7, 9), List(1, 2, 3) | List(4,5,6) |
| Karatsuba | shift | setupStage9 | List(1, 2, 3), k=2 | List(0,0,1,2,3) |
| Karatsuba | clean | setupStage10 | List(1, 2, 3, 0, 0) | List(1,2,3) |
| Karatsuba | clean | setupStage10 | List(0, 0, 0) | List(0) |

### Objective: Verify correctness of Karatsuba polynomial multiplication

| Class | Method | Scenario | Input Values | Expected Result |
|-------|--------|----------|--------------|-----------------|
| Karatsuba | multiply | setupStage8 | List(1), List(2) | List(2) |
| Karatsuba | multiply | setupStage9 | List(2, 1), List(1, 1) | List(2,3,1) |
| Karatsuba | multiply | setupStage9 | List(2, 1, 1), List(2, 1, 1) | List(4,4,5,2,1) |
| Karatsuba | multiply | setupStage10 | List(0), List(5, 3, 2) | List(0) |
| Karatsuba | multiply | setupStage11 | [[1,2,3], [4,5,6]] from JSON | [4,13,28,27,18] |
| Karatsuba | multiply | setupStage12 | [[1,2,3,4,5], [5,4,3,2,1]] from JSON | [5,14,26,40,55,40,26,14,5] |
| Karatsuba | multiply | setupStage13 | [[1,2,3,4,5,6,7,8], [8,7,6,5,4,3,2,1]] from JSON | [8,23,52,110,200,266,292,276,248,220,190,152,104,56,8] |

### Objective: Verify correctness of XOR auxiliary functions

| Class | Method | Scenario | Input Values | Expected Result |
|-------|--------|----------|--------------|-----------------|
| XorLogic | getBit | setupStage16 | n=5, pos=0 | 1 |
| XorLogic | getBit | setupStage16 | n=5, pos=1 | 0 |
| XorLogic | getBit | setupStage16 | n=5, pos=2 | 1 |
| XorLogic | splitByBit | setupStage16 | List(1,2,3,4), bit=0 | (List(2,4), List(1,3)) |
| XorLogic | maxTuple | setupStage16 | (5, (1,2)), (3, (3,4)) | (5, (1,2)) |

### Objective: Verify correctness of Maximum XOR algorithm

| Class | Method | Scenario | Input Values | Expected Result |
|-------|--------|----------|--------------|-----------------|
| XorLogic | maxXorPair | setupStage14 | Nil | (0, (0,0)) |
| XorLogic | maxXorPair | setupStage15 | List(5) | (0, (0,0)) |
| XorLogic | maxXorPair | setupStage16 | List(3, 10) | (9, (3,10)) or (9, (10,3)) |
| XorLogic | maxXorPair | setupStage16 | List(8, 1, 2, 15) | (15, (8,7)) or similar max XOR pair |
| XorLogic | maxXorPair | setupStage16 | List(5, 2, 4, 6, 7) | Maximum XOR value with corresponding pair |
| XorLogic | maxXorPair | setupStage17 | Small test list from JSON | Maximum XOR value for small case |
| XorLogic | maxXorPair | setupStage18 | Medium test list from JSON | Maximum XOR value for medium case |
| XorLogic | maxXorPair | setupStage19 | Large test list from JSON | Maximum XOR value for large case |

---

## Notes

- **Toy Tests**: Focus on basic functionality and edge cases with hardcoded simple inputs
- **Scalability Tests**: Use JSON files to load structured test data for small, medium, and large inputs
- **Expected Results**: For scalability tests, expected results are either loaded from JSON files or computed by the algorithm itself (as seen in the current implementation)
- **Tail Recursion**: All recursive functions use `@tailrec` annotation to ensure stack safety
- **Pure Functions**: All methods are pure with no side effects, making them deterministic and easy to test
