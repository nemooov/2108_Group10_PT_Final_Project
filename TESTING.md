# Testing Evidence
Every test was run with `java bankers_algo < tests/<id>.txt`. The **actual output** below is the real captured output (also saved as `tests/<id>.out.txt`). A test passes when every expected line appears in the output.
| ID | Test | Result |
|---|---|---|
| TC1 | Safety check on a safe system (textbook data) | PASS |
| TC2 | Valid request is granted | PASS |
| TC3 | Request that would be unsafe must wait (rollback) | PASS |
| TC4 | Request above Need is an error | PASS |
| TC5 | Request above Available must wait | PASS |
| TC6 | Invalid input is rejected and nothing is saved | PASS |
| TC7 | Typing mistakes are caught and re-asked | PASS |
| TC8 | Own system that is unsafe | PASS |
| TC9 | Menu is blocked until a system is entered | PASS |
| TC10 | Unsafe state: nobody can finish | PASS |
| TC11 | Help screen works without a system | PASS |

## TC1 - Safety check on a safe system (textbook data)

**Scenario:** Option 1 with the textbook data, then option 2

**Input typed** (`tests/TC1.txt`):

```
1
5
3
10 5 7
0 1 0
2 0 0
3 0 2
2 1 1
0 0 2
7 5 3
3 2 2
9 0 2
2 2 2
4 3 3
2
0
```

**Expected:** Available [3,3,2]; SAFE with sequence <P1, P3, P0, P2, P4>.
Lines that must appear:
- `Available = [3, 3, 2]`
- `RESULT: SAFE STATE`
- `<P1, P3, P0, P2, P4>`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Number of processes (n >= 1): Number of resource types (m >= 1): Total instances of each resource (3 value(s)): Allocation of P0 (3 value(s)): Allocation of P1 (3 value(s)): Allocation of P2 (3 value(s)): Allocation of P3 (3 value(s)): Allocation of P4 (3 value(s)): Max of P0 (3 value(s)): Max of P1 (3 value(s)): Max of P2 (3 value(s)): Max of P3 (3 value(s)): Max of P4 (3 value(s)): 
  Total     = [10, 5, 7]
  Available = [3, 3, 2]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [0, 1, 0]      [7, 5, 3]      [7, 4, 3]     
  P1    [2, 0, 0]      [3, 2, 2]      [1, 2, 2]     
  P2    [3, 0, 2]      [9, 0, 2]      [6, 0, 0]     
  P3    [2, 1, 1]      [2, 2, 2]      [0, 1, 1]     
  P4    [0, 0, 2]      [4, 3, 3]      [4, 3, 1]     

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: 
  Total     = [10, 5, 7]
  Available = [3, 3, 2]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [0, 1, 0]      [7, 5, 3]      [7, 4, 3]     
  P1    [2, 0, 0]      [3, 2, 2]      [1, 2, 2]     
  P2    [3, 0, 2]      [9, 0, 2]      [6, 0, 0]     
  P3    [2, 1, 1]      [2, 2, 2]      [0, 1, 1]     
  P4    [0, 0, 2]      [4, 3, 3]      [4, 3, 1]     

========================================================
  SAFETY ALGORITHM
========================================================
Work = Available = [3, 3, 2]
  P0: Need [7, 4, 3] <= Work [3, 3, 2] ?  NO
  P1: Need [1, 2, 2] <= Work [3, 3, 2] ?  YES
  -> P1 finishes. Work = [5, 3, 2]
  P0: Need [7, 4, 3] <= Work [5, 3, 2] ?  NO
  P2: Need [6, 0, 0] <= Work [5, 3, 2] ?  NO
  P3: Need [0, 1, 1] <= Work [5, 3, 2] ?  YES
  -> P3 finishes. Work = [7, 4, 3]
  P0: Need [7, 4, 3] <= Work [7, 4, 3] ?  YES
  -> P0 finishes. Work = [7, 5, 3]
  P2: Need [6, 0, 0] <= Work [7, 5, 3] ?  YES
  -> P2 finishes. Work = [10, 5, 5]
  P4: Need [4, 3, 1] <= Work [10, 5, 5] ?  YES
  -> P4 finishes. Work = [10, 5, 7]

Finish = [true, true, true, true, true]
RESULT: SAFE STATE.  Safe sequence: <P1, P3, P0, P2, P4>

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC2 - Valid request is granted

**Scenario:** P1 requests [1,0,2]

**Input typed** (`tests/TC2.txt`):

```
1
5
3
10 5 7
0 1 0
2 0 0
3 0 2
2 1 1
0 0 2
7 5 3
3 2 2
9 0 2
2 2 2
4 3 3
3
1
1 0 2
0
```

**Expected:** Both checks pass, the pretend state is safe, so GRANT.
Lines that must appear:
- `Step 1: Request <= Need [1, 2, 2] ?  YES`
- `Step 2: Request <= Available [3, 3, 2] ?  YES`
- `Available      = [2, 3, 0]`
- `DECISION: GRANT`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Number of processes (n >= 1): Number of resource types (m >= 1): Total instances of each resource (3 value(s)): Allocation of P0 (3 value(s)): Allocation of P1 (3 value(s)): Allocation of P2 (3 value(s)): Allocation of P3 (3 value(s)): Allocation of P4 (3 value(s)): Max of P0 (3 value(s)): Max of P1 (3 value(s)): Max of P2 (3 value(s)): Max of P3 (3 value(s)): Max of P4 (3 value(s)): 
  Total     = [10, 5, 7]
  Available = [3, 3, 2]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [0, 1, 0]      [7, 5, 3]      [7, 4, 3]     
  P1    [2, 0, 0]      [3, 2, 2]      [1, 2, 2]     
  P2    [3, 0, 2]      [9, 0, 2]      [6, 0, 0]     
  P3    [2, 1, 1]      [2, 2, 2]      [0, 1, 1]     
  P4    [0, 0, 2]      [4, 3, 3]      [4, 3, 1]     

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Which process is asking for more resources (0 to 4)? How many more of each resource does it want (3 value(s))? 
========================================================
  REQUEST CHECK: P1 requests [1, 0, 2]
========================================================
Step 1: Request <= Need [1, 2, 2] ?  YES
Step 2: Request <= Available [3, 3, 2] ?  YES
Step 3: Pretend to grant it:
        Available      = [2, 3, 0]
        Allocation[P1] = [3, 0, 2]
        Need[P1]       = [0, 2, 0]
Step 4: Is that state safe?

========================================================
  SAFETY ALGORITHM
========================================================
Work = Available = [2, 3, 0]
  P0: Need [7, 4, 3] <= Work [2, 3, 0] ?  NO
  P1: Need [0, 2, 0] <= Work [2, 3, 0] ?  YES
  -> P1 finishes. Work = [5, 3, 2]
  P0: Need [7, 4, 3] <= Work [5, 3, 2] ?  NO
  P2: Need [6, 0, 0] <= Work [5, 3, 2] ?  NO
  P3: Need [0, 1, 1] <= Work [5, 3, 2] ?  YES
  -> P3 finishes. Work = [7, 4, 3]
  P0: Need [7, 4, 3] <= Work [7, 4, 3] ?  YES
  -> P0 finishes. Work = [7, 5, 3]
  P2: Need [6, 0, 0] <= Work [7, 5, 3] ?  YES
  -> P2 finishes. Work = [10, 5, 5]
  P4: Need [4, 3, 1] <= Work [10, 5, 5] ?  YES
  -> P4 finishes. Work = [10, 5, 7]

Finish = [true, true, true, true, true]
RESULT: SAFE STATE.  Safe sequence: <P1, P3, P0, P2, P4>

DECISION: GRANT - the new allocation is kept.

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC3 - Request that would be unsafe must wait (rollback)

**Scenario:** P1 gets [1,0,2], then P0 requests [0,2,0]

**Input typed** (`tests/TC3.txt`):

```
1
5
3
10 5 7
0 1 0
2 0 0
3 0 2
2 1 1
0 0 2
7 5 3
3 2 2
9 0 2
2 2 2
4 3 3
3
1
1 0 2
3
0
0 2 0
0
```

**Expected:** Enough resources exist, but the pretend state is UNSAFE, so WAIT and nothing is changed.
Lines that must appear:
- `Available      = [2, 1, 0]`
- `RESULT: UNSAFE STATE`
- `DECISION: WAIT - granting would be unsafe`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Number of processes (n >= 1): Number of resource types (m >= 1): Total instances of each resource (3 value(s)): Allocation of P0 (3 value(s)): Allocation of P1 (3 value(s)): Allocation of P2 (3 value(s)): Allocation of P3 (3 value(s)): Allocation of P4 (3 value(s)): Max of P0 (3 value(s)): Max of P1 (3 value(s)): Max of P2 (3 value(s)): Max of P3 (3 value(s)): Max of P4 (3 value(s)): 
  Total     = [10, 5, 7]
  Available = [3, 3, 2]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [0, 1, 0]      [7, 5, 3]      [7, 4, 3]     
  P1    [2, 0, 0]      [3, 2, 2]      [1, 2, 2]     
  P2    [3, 0, 2]      [9, 0, 2]      [6, 0, 0]     
  P3    [2, 1, 1]      [2, 2, 2]      [0, 1, 1]     
  P4    [0, 0, 2]      [4, 3, 3]      [4, 3, 1]     

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Which process is asking for more resources (0 to 4)? How many more of each resource does it want (3 value(s))? 
========================================================
  REQUEST CHECK: P1 requests [1, 0, 2]
========================================================
Step 1: Request <= Need [1, 2, 2] ?  YES
Step 2: Request <= Available [3, 3, 2] ?  YES
Step 3: Pretend to grant it:
        Available      = [2, 3, 0]
        Allocation[P1] = [3, 0, 2]
        Need[P1]       = [0, 2, 0]
Step 4: Is that state safe?

========================================================
  SAFETY ALGORITHM
========================================================
Work = Available = [2, 3, 0]
  P0: Need [7, 4, 3] <= Work [2, 3, 0] ?  NO
  P1: Need [0, 2, 0] <= Work [2, 3, 0] ?  YES
  -> P1 finishes. Work = [5, 3, 2]
  P0: Need [7, 4, 3] <= Work [5, 3, 2] ?  NO
  P2: Need [6, 0, 0] <= Work [5, 3, 2] ?  NO
  P3: Need [0, 1, 1] <= Work [5, 3, 2] ?  YES
  -> P3 finishes. Work = [7, 4, 3]
  P0: Need [7, 4, 3] <= Work [7, 4, 3] ?  YES
  -> P0 finishes. Work = [7, 5, 3]
  P2: Need [6, 0, 0] <= Work [7, 5, 3] ?  YES
  -> P2 finishes. Work = [10, 5, 5]
  P4: Need [4, 3, 1] <= Work [10, 5, 5] ?  YES
  -> P4 finishes. Work = [10, 5, 7]

Finish = [true, true, true, true, true]
RESULT: SAFE STATE.  Safe sequence: <P1, P3, P0, P2, P4>

DECISION: GRANT - the new allocation is kept.

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Which process is asking for more resources (0 to 4)? How many more of each resource does it want (3 value(s))? 
========================================================
  REQUEST CHECK: P0 requests [0, 2, 0]
========================================================
Step 1: Request <= Need [7, 4, 3] ?  YES
Step 2: Request <= Available [2, 3, 0] ?  YES
Step 3: Pretend to grant it:
        Available      = [2, 1, 0]
        Allocation[P0] = [0, 3, 0]
        Need[P0]       = [7, 2, 3]
Step 4: Is that state safe?

========================================================
  SAFETY ALGORITHM
========================================================
Work = Available = [2, 1, 0]
  P0: Need [7, 2, 3] <= Work [2, 1, 0] ?  NO
  P1: Need [0, 2, 0] <= Work [2, 1, 0] ?  NO
  P2: Need [6, 0, 0] <= Work [2, 1, 0] ?  NO
  P3: Need [0, 1, 1] <= Work [2, 1, 0] ?  NO
  P4: Need [4, 3, 1] <= Work [2, 1, 0] ?  NO

Finish = [false, false, false, false, false]
RESULT: UNSAFE STATE. No safe sequence exists (deadlock is possible).

DECISION: WAIT - granting would be unsafe, so it is rolled back.

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC4 - Request above Need is an error

**Scenario:** P1 requests [4,0,0] but Need is [1,2,2]

**Input typed** (`tests/TC4.txt`):

```
1
5
3
10 5 7
0 1 0
2 0 0
3 0 2
2 1 1
0 0 2
7 5 3
3 2 2
9 0 2
2 2 2
4 3 3
3
1
4 0 0
0
```

**Expected:** Step 1 fails: ERROR.
Lines that must appear:
- `Request <= Need [1, 2, 2] ?  NO`
- `DECISION: ERROR`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Number of processes (n >= 1): Number of resource types (m >= 1): Total instances of each resource (3 value(s)): Allocation of P0 (3 value(s)): Allocation of P1 (3 value(s)): Allocation of P2 (3 value(s)): Allocation of P3 (3 value(s)): Allocation of P4 (3 value(s)): Max of P0 (3 value(s)): Max of P1 (3 value(s)): Max of P2 (3 value(s)): Max of P3 (3 value(s)): Max of P4 (3 value(s)): 
  Total     = [10, 5, 7]
  Available = [3, 3, 2]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [0, 1, 0]      [7, 5, 3]      [7, 4, 3]     
  P1    [2, 0, 0]      [3, 2, 2]      [1, 2, 2]     
  P2    [3, 0, 2]      [9, 0, 2]      [6, 0, 0]     
  P3    [2, 1, 1]      [2, 2, 2]      [0, 1, 1]     
  P4    [0, 0, 2]      [4, 3, 3]      [4, 3, 1]     

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Which process is asking for more resources (0 to 4)? How many more of each resource does it want (3 value(s))? 
========================================================
  REQUEST CHECK: P1 requests [4, 0, 0]
========================================================
Step 1: Request <= Need [1, 2, 2] ?  NO
DECISION: ERROR - the process asked for more than its maximum claim.

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC5 - Request above Available must wait

**Scenario:** After the P1 grant, P4 requests [3,3,0] but Available is [2,3,0]

**Input typed** (`tests/TC5.txt`):

```
1
5
3
10 5 7
0 1 0
2 0 0
3 0 2
2 1 1
0 0 2
7 5 3
3 2 2
9 0 2
2 2 2
4 3 3
3
1
1 0 2
3
4
3 3 0
0
```

**Expected:** Step 2 fails: WAIT.
Lines that must appear:
- `Request <= Available [2, 3, 0] ?  NO`
- `DECISION: WAIT - the resources are not available`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Number of processes (n >= 1): Number of resource types (m >= 1): Total instances of each resource (3 value(s)): Allocation of P0 (3 value(s)): Allocation of P1 (3 value(s)): Allocation of P2 (3 value(s)): Allocation of P3 (3 value(s)): Allocation of P4 (3 value(s)): Max of P0 (3 value(s)): Max of P1 (3 value(s)): Max of P2 (3 value(s)): Max of P3 (3 value(s)): Max of P4 (3 value(s)): 
  Total     = [10, 5, 7]
  Available = [3, 3, 2]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [0, 1, 0]      [7, 5, 3]      [7, 4, 3]     
  P1    [2, 0, 0]      [3, 2, 2]      [1, 2, 2]     
  P2    [3, 0, 2]      [9, 0, 2]      [6, 0, 0]     
  P3    [2, 1, 1]      [2, 2, 2]      [0, 1, 1]     
  P4    [0, 0, 2]      [4, 3, 3]      [4, 3, 1]     

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Which process is asking for more resources (0 to 4)? How many more of each resource does it want (3 value(s))? 
========================================================
  REQUEST CHECK: P1 requests [1, 0, 2]
========================================================
Step 1: Request <= Need [1, 2, 2] ?  YES
Step 2: Request <= Available [3, 3, 2] ?  YES
Step 3: Pretend to grant it:
        Available      = [2, 3, 0]
        Allocation[P1] = [3, 0, 2]
        Need[P1]       = [0, 2, 0]
Step 4: Is that state safe?

========================================================
  SAFETY ALGORITHM
========================================================
Work = Available = [2, 3, 0]
  P0: Need [7, 4, 3] <= Work [2, 3, 0] ?  NO
  P1: Need [0, 2, 0] <= Work [2, 3, 0] ?  YES
  -> P1 finishes. Work = [5, 3, 2]
  P0: Need [7, 4, 3] <= Work [5, 3, 2] ?  NO
  P2: Need [6, 0, 0] <= Work [5, 3, 2] ?  NO
  P3: Need [0, 1, 1] <= Work [5, 3, 2] ?  YES
  -> P3 finishes. Work = [7, 4, 3]
  P0: Need [7, 4, 3] <= Work [7, 4, 3] ?  YES
  -> P0 finishes. Work = [7, 5, 3]
  P2: Need [6, 0, 0] <= Work [7, 5, 3] ?  YES
  -> P2 finishes. Work = [10, 5, 5]
  P4: Need [4, 3, 1] <= Work [10, 5, 5] ?  YES
  -> P4 finishes. Work = [10, 5, 7]

Finish = [true, true, true, true, true]
RESULT: SAFE STATE.  Safe sequence: <P1, P3, P0, P2, P4>

DECISION: GRANT - the new allocation is kept.

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Which process is asking for more resources (0 to 4)? How many more of each resource does it want (3 value(s))? 
========================================================
  REQUEST CHECK: P4 requests [3, 3, 0]
========================================================
Step 1: Request <= Need [4, 3, 1] ?  YES
Step 2: Request <= Available [2, 3, 0] ?  NO
DECISION: WAIT - the resources are not available.

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC6 - Invalid input is rejected and nothing is saved

**Scenario:** P0 Allocation [3,0] exceeds its Max [2,0]

**Input typed** (`tests/TC6.txt`):

```
1
2
2
5 5
3 0
0 0
2 0
2 0
0
```

**Expected:** The input is rejected with a clear message.
Lines that must appear:
- `INVALID INPUT: Allocation of P0 for R0 (3) is greater than its Max (2)`
- `Nothing was saved`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Number of processes (n >= 1): Number of resource types (m >= 1): Total instances of each resource (2 value(s)): Allocation of P0 (2 value(s)): Allocation of P1 (2 value(s)): Max of P0 (2 value(s)): Max of P1 (2 value(s)): INVALID INPUT: Allocation of P0 for R0 (3) is greater than its Max (2).
Nothing was saved. Try again.

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC7 - Typing mistakes are caught and re-asked

**Scenario:** Letters, a negative number, an out-of-range number and a wrong count

**Input typed** (`tests/TC7.txt`):

```
abc
-1
9
1 2
0
```

**Expected:** Each bad entry gets a message and the program asks again.
Lines that must appear:
- `is not a whole number`
- `is negative`
- `Please enter a number from 0 to 5`
- `Goodbye.`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose:   'abc' is not a whole number.
Choose:   '-1' is negative. Use 0 or more.
Choose:   Please enter a number from 0 to 5.
Choose:   Please enter exactly 1 number(s), separated by spaces.
Choose: Goodbye.
```

**Result: PASS**

## TC8 - Own system that is unsafe

**Scenario:** 2 processes, 1 resource, Total 2, each holds 1 and may need 2 (Available 0)

**Input typed** (`tests/TC8.txt`):

```
1
2
1
2
1
1
2
2
2
0
```

**Expected:** Nobody can finish, so UNSAFE.
Lines that must appear:
- `Available = [0]`
- `RESULT: UNSAFE STATE`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Number of processes (n >= 1): Number of resource types (m >= 1): Total instances of each resource (1 value(s)): Allocation of P0 (1 value(s)): Allocation of P1 (1 value(s)): Max of P0 (1 value(s)): Max of P1 (1 value(s)): 
  Total     = [2]
  Available = [0]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [1]            [2]            [1]           
  P1    [1]            [2]            [1]           

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: 
  Total     = [2]
  Available = [0]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [1]            [2]            [1]           
  P1    [1]            [2]            [1]           

========================================================
  SAFETY ALGORITHM
========================================================
Work = Available = [0]
  P0: Need [1] <= Work [0] ?  NO
  P1: Need [1] <= Work [0] ?  NO

Finish = [false, false]
RESULT: UNSAFE STATE. No safe sequence exists (deadlock is possible).

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC9 - Menu is blocked until a system is entered

**Scenario:** Choose option 2 before entering anything

**Input typed** (`tests/TC9.txt`):

```
2
0
```

**Expected:** The program refuses politely.
Lines that must appear:
- `Enter a system first`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Enter a system first (option 1). Option 5 shows help.

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC10 - Unsafe state: nobody can finish

**Scenario:** 3 processes, 2 resources, Total [6,4]; Available [1,1] but every Need is [2,2]

**Input typed** (`tests/TC10.txt`):

```
1
3
2
6 4
2 1
2 1
1 1
4 3
4 3
3 3
2
0
```

**Expected:** Every process needs [2,2] but only [1,1] is free: UNSAFE.
Lines that must appear:
- `Available = [1, 1]`
- `Need [2, 2] <= Work [1, 1] ?  NO`
- `RESULT: UNSAFE STATE`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Number of processes (n >= 1): Number of resource types (m >= 1): Total instances of each resource (2 value(s)): Allocation of P0 (2 value(s)): Allocation of P1 (2 value(s)): Allocation of P2 (2 value(s)): Max of P0 (2 value(s)): Max of P1 (2 value(s)): Max of P2 (2 value(s)): 
  Total     = [6, 4]
  Available = [1, 1]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [2, 1]         [4, 3]         [2, 2]        
  P1    [2, 1]         [4, 3]         [2, 2]        
  P2    [1, 1]         [3, 3]         [2, 2]        

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: 
  Total     = [6, 4]
  Available = [1, 1]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [2, 1]         [4, 3]         [2, 2]        
  P1    [2, 1]         [4, 3]         [2, 2]        
  P2    [1, 1]         [3, 3]         [2, 2]        

========================================================
  SAFETY ALGORITHM
========================================================
Work = Available = [1, 1]
  P0: Need [2, 2] <= Work [1, 1] ?  NO
  P1: Need [2, 2] <= Work [1, 1] ?  NO
  P2: Need [2, 2] <= Work [1, 1] ?  NO

Finish = [false, false, false]
RESULT: UNSAFE STATE. No safe sequence exists (deadlock is possible).

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**

## TC11 - Help screen works without a system

**Scenario:** Choose option 5 first

**Input typed** (`tests/TC11.txt`):

```
5
0
```

**Expected:** The help screen is shown even before a system is entered.
Lines that must appear:
- `HELP`
- `Need      = Max - Allocation`
- `Reading the results`
- `Goodbye.`

**Actual output:**

```

========================================================
  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator
========================================================

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: 
========================================================
  HELP
========================================================
  What this program does
    It checks whether a system of processes and resources is in a SAFE
    state (no deadlock possible) and decides resource requests.

  Menu
    1  Enter a system   Type in your processes and resources first.
    2  Safety check     Shows step by step if a safe sequence exists.
    3  Resource request Asks if a process may get more resources.
    4  Show tables      Shows Total, Available, Allocation, Max, Need.
    5  Help             Shows this screen.
    0  Exit

  Entering a system (option 1), in this order
    - number of processes and number of resource types
    - Total: how many of each resource exist, e.g. 10 5 7
    - Allocation of every process: what it holds now, e.g. 0 1 0
    - Max of every process: the most it will ever need, e.g. 7 5 3
    Use whole numbers (0 or more) separated by spaces.

  Terms
    Need      = Max - Allocation (what a process may still ask for)
    Available = Total - all Allocation (what is free right now)
    Work      = free resources while the safety check runs

  Reading the results
    SAFE    every process can finish; a safe sequence is shown.
    UNSAFE  nobody can finish; deadlock is possible.
    GRANT   the request is safe and is kept.
    WAIT    not enough free resources, or granting would be unsafe.
    ERROR   the request is more than the process's Need.

  Try this: Total 10 5 7, 5 processes
    Allocation P0-P4: 0 1 0 / 2 0 0 / 3 0 2 / 2 1 1 / 0 0 2
    Max        P0-P4: 7 5 3 / 3 2 2 / 9 0 2 / 2 2 2 / 4 3 3

--------------------------------------------------------
                      MAIN   MENU
--------------------------------------------------------
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
--------------------------------------------------------
Choose: Goodbye.
```

**Result: PASS**
