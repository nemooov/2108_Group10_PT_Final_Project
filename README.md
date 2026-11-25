# 2108_Group10_PT_Final_Project
# Banker's Algorithm Simulator

A Java console application that shows how an operating system *avoids deadlock* with the Banker's Algorithm. Enter processes and resources, run the safety check step by step, and test whether a resource request should be granted.

One file. No libraries. Java 8 or newer.


## Contents

- [Algorithms](#algorithms)
- [Features](#features)
- [How to Run](#how-to-run)
- [Menu](#menu)
- [Sample Run](#sample-run)
- [How It Works](#how-it-works)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Members](#members)
- [Academic Information](#academic-information)
- [Disclosure of Assistance and Tools](#disclosure-of-assistance-and-tools)

## Algorithms

- *Safety Algorithm* - decides whether the current state is SAFE or UNSAFE and prints a safe sequence.
- *Resource-Request Algorithm* - decides whether a process's request is GRANTED, must WAIT, or is an ERROR.

## Features

- Interactive console menu with a built-in *Help* screen
- Manual input of processes, Total, Allocation and Max
- Input validation (letters, negatives, wrong counts, Allocation above Max, Total below the allocated amount)
- Automatic *Need* and *Available* calculation
- Step-by-step *safety trace* (Work after every process finishes) and the safe sequence
- Request check in five visible steps with automatic *rollback* when unsafe
- 11 documented test cases with expected and actual results

## How to Run

1. Install a JDK (Java 8 or newer).
2. Download or clone this repository.
3. Open a terminal in the project folder and run:

javac bankers_algo.java
java bankers_algo

In an IDE (IntelliJ, Eclipse, VS Code), open bankers_algo.java and run main.

To replay a saved test without typing: java bankers_algo < tests/TC1.txt

The full instructions are in [USER_GUIDE.md](USER_GUIDE.md).

## Menu

| Option | What it does |
|---|---|
| 1 | Enter a system (processes, resources, Total, Allocation, Max) |
| 2 | Safety check with a step-by-step trace |
| 3 | Resource request (GRANT / WAIT / ERROR) |
| 4 | Show Total, Available, Allocation, Max and Need |
| 5 | Help |
| 0 | Exit |

## Sample Run

Entering 5 processes and 3 resources (Total 10 5 7) and viewing the tables (option 4):

  Total     = [10, 5, 7]
  Available = [3, 3, 2]

        Allocation     Max            Need          
  --------------------------------------------------
  P0    [0, 1, 0]      [7, 5, 3]      [7, 4, 3]     
  P1    [2, 0, 0]      [3, 2, 2]      [1, 2, 2]     
  P2    [3, 0, 2]      [9, 0, 2]      [6, 0, 0]     
  P3    [2, 1, 1]      [2, 2, 2]      [0, 1, 1]     
  P4    [0, 0, 2]      [4, 3, 3]      [4, 3, 1]     

Safety check (option 2):

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

Resource request (option 3): P1 asks for 1 0 2:

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

When granting would lead to an unsafe state, the request waits and nothing changes:

DECISION: WAIT - granting would be unsafe, so it is rolled back.

## How It Works

| Term | Formula / meaning |
|---|---|
| Need | Max - Allocation |
| Available | Total - sum of all Allocation |
| Work | Free resources during the safety check; starts as Available |
| Safe state | Every process can finish in some order |

*Safety algorithm*

1. Work = Available, Finish[i] = false for all processes.
2. Find an unfinished process with Need <= Work.
3. It finishes: Work = Work + Allocation, Finish[i] = true.
4. Repeat. If everyone finishes the state is *SAFE*; if none can proceed it is *UNSAFE*.

*Resource-request algorithm*

1. If Request > Need then *ERROR* (the process exceeded its maximum claim).
2. If Request > Available then *WAIT* (resources are not free).
3. Pretend to grant: Available -= Request, Allocation += Request, Need -= Request.
4. Run the safety algorithm on the pretend state.
5. Safe: *GRANT*. Unsafe: *WAIT* and the old state is restored.

## Testing

Eleven test cases cover a safe state, GRANT, unsafe WAIT with rollback, ERROR, WAIT for unavailable resources, invalid input, typing mistakes, an unsafe system, the blocked menu and the help screen. Each lists the input, the expected result and the real output. See [TESTING.md](TESTING.md).

## Project Structure

bankers_algo.java   the whole program
README.md           this file
USER_GUIDE.md       how to run, what to type, how to read the output
TESTING.md          test cases with expected and actual results
tests/              TCn.txt = typed input, TCn.out.txt = captured output

## Members

- Villegas, Nemesio P.
- Montalbo, Alexander John P.
- Viceral, Jhayzel Liann B.
- Rosas, Julliane Faith R.

## Academic Information
PT 101 - Platform Technologies
*Section: BSIT-2108
Batangas State University Alangilan Campus
Academic Year:2026-2027


## Disclosure of Assistance and Tools

This program was written with the help of an AI assistant (Claude, by Anthropic), which enhanced the code and comments. The group reviewed, ran and tested the program and is responsible for understanding and explaining it. The algorithms follow the instructor-assigned topic (Banker's Algorithm).
