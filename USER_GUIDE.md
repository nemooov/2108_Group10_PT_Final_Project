# User Guide

## 1. Requirements and running

Java 8 or newer (JDK for `javac`). From the folder holding `bankers_algo.java`:

```
javac bankers_algo.java
java bankers_algo
```

In an IDE (IntelliJ, Eclipse, VS Code) just run `bankers_algo.main`. Type in the console and press Enter.

## 2. The menu

```
  1  Enter a system
  2  Safety check
  3  Resource request
  4  Show tables
  5  Help
  0  Exit
```

| Choice | What it does |
|---|---|
| 1 | Type in your system (see section 3). |
| 2 | Show the tables and run the safety algorithm step by step. |
| 3 | Ask whether a process may be given extra resources. |
| 4 | Show Total, Available, Allocation, Max and Need. |
| 5 | Show the help screen (works at any time, includes a sample system to try). |
| 0 | Exit. |

Choices 2, 3 and 4 need a system first (choose 1); otherwise the program says so.

## 3. Entering a system (choice 1)

All numbers are whole numbers, 0 or more, separated by spaces. The program asks, in order:

| Prompt | What to type | Example |
|---|---|---|
| Number of processes | one number, 1-100 | `5` |
| Number of resource types | one number, 1-100 | `3` |
| Total instances of each resource | one number per resource | `10 5 7` |
| Allocation of Pi | what Pi holds now, one number per resource | `0 1 0` |
| Max of Pi | the most Pi will ever need | `7 5 3` |

(Allocation is asked for every process first, then Max for every process.)

Rejected input:

- letters, decimals or negatives: "is not a whole number" / "is negative", then it asks again
- wrong amount of numbers: "Please enter exactly N number(s)", then it asks again
- Allocation larger than Max, or Total smaller than the allocated amount: "INVALID INPUT ... Nothing was saved."

## 4. Reading the output

**Tables (choice 4)**

```
Total     = [10, 5, 7]       instances that exist
Available = [3, 3, 2]        Total minus everything allocated
      Allocation     Max            Need
P1    [2, 0, 0]      [3, 2, 2]      [1, 2, 2]
```

Need is what a process may still ask for (Max - Allocation). Column order is R0, R1, R2...

**Safety check (choice 2)**

```
Work = Available = [3, 3, 2]
  P0: Need [7, 4, 3] <= Work [3, 3, 2] ?  NO
  P1: Need [1, 2, 2] <= Work [3, 3, 2] ?  YES
  -> P1 finishes. Work = [5, 3, 2]
...
RESULT: SAFE STATE.  Safe sequence: <P1, P3, P0, P2, P4>
```

Each line asks "can this process's Need be met by what is free (Work)?". If YES, the process finishes and gives back what it held, so Work grows. If every process finishes the state is **SAFE** and the order is a safe sequence. If a round finds nobody who fits, the result is **UNSAFE** (deadlock is possible). More than one safe sequence can exist; any one proves safety.

**Resource request (choice 3)** - you give the process that is asking (for example `1` for P1) and how many more of each resource it wants (for example `1 0 2` = one more R0, none of R1, two more R2).

| Step | Check | If it fails |
|---|---|---|
| 1 | Request <= Need | **ERROR**: the process asked for more than its maximum |
| 2 | Request <= Available | **WAIT**: resources are not free yet |
| 3-4 | Pretend to grant it, then run the safety check | **WAIT**: granting would be unsafe, rolled back |
| 5 | Pretend state is safe | **GRANT**: the new allocation is kept |

After a GRANT the tables change; choose 4 to see them. After ERROR or WAIT nothing changes.

## 5. Example session

Type `1` and enter the sample system from the help screen (Total `10 5 7`, five processes), then:

```
Choose: 3
Which process is asking for more resources (0 to 4)? 1
How many more of each resource does it want (3 value(s))? 1 0 2
...
DECISION: GRANT - the new allocation is kept.
```

More examples with full output are in `TESTING.md`.
