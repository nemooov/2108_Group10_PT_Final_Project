import java.util.Arrays;
import java.util.Scanner;

public class bankers_algo {

    static int n, m;
    static int[] total;
    static int[][] alloc;
    static int[][] max;
    static boolean loaded = false;

    static void loadSample() {
        n = 5;
        m = 3;
        total = new int[] {10, 5, 7};
        alloc = new int[][] {{0, 1, 0}, {2, 0, 0}, {3, 0, 2}, {2, 1, 1}, {0, 0, 2}};
        max = new int[][] {{7, 5, 3}, {3, 2, 2}, {9, 0, 2}, {2, 2, 2}, {4, 3, 3}};
        loaded = true;
    }

    static int[][] needOf(int[][] a) {
        int[][] need = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - a[i][j];
            }
        }
        return need;
    }

    static int[] availableOf(int[][] a) {
        int[] avail = total.clone();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                avail[j] -= a[i][j];
            }
        }
        return avail;
    }

    static boolean fits(int[] x, int[] y) {
        for (int j = 0; j < x.length; j++) {
            if (x[j] > y[j]) {
                return false;
            }
        }
        return true;
    }

    static void showTables() {
        int[][] need = needOf(alloc);
        System.out.println();
        System.out.println("  " + "Total     = " + Arrays.toString(total));
        System.out.println("  " + "Available = " + Arrays.toString(availableOf(alloc)));
        System.out.println();
        System.out.println("  " + String.format("%-5s %-14s %-14s %-14s", "", "Allocation", "Max", "Need"));
        System.out.println("  " + rule(50, '-'));
        for (int i = 0; i < n; i++) {
            System.out.println("  " + String.format("%-5s", "P" + i)
                    + String.format(" %-14s %-14s ", Arrays.toString(alloc[i]), Arrays.toString(max[i]))
                    + String.format("%-14s", Arrays.toString(need[i])));
        }
    }

    static Scanner in = new Scanner(System.in);

    static String rule(int w, char ch) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < w; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    static void title(String text) {
        System.out.println();
        System.out.println(rule(56, '='));
        System.out.println("  " + text);
        System.out.println(rule(56, '='));
    }

    static void enterSystem() {
        int n2 = readInt("Number of processes (n >= 1): ", 1, 100);
        int m2 = readInt("Number of resource types (m >= 1): ", 1, 100);
        int[] total2 = readVector("Total instances of each resource (" + m2 + " value(s)): ", m2);
        int[][] alloc2 = new int[n2][];
        int[][] max2 = new int[n2][];
        for (int i = 0; i < n2; i++) {
            alloc2[i] = readVector("Allocation of P" + i + " (" + m2 + " value(s)): ", m2);
        }
        for (int i = 0; i < n2; i++) {
            max2[i] = readVector("Max of P" + i + " (" + m2 + " value(s)): ", m2);
        }

        String problem = check(total2, alloc2, max2);
        if (problem != null) {
            System.out.println("INVALID INPUT: " + problem);
            System.out.println("Nothing was saved. Try again.");
            return;
        }
        n = n2;
        m = m2;
        total = total2;
        alloc = alloc2;
        max = max2;
        loaded = true;
        showTables();
    }

    static String check(int[] t, int[][] a, int[][] mx) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < t.length; j++) {
                if (a[i][j] > mx[i][j]) {
                    return "Allocation of P" + i + " for R" + j + " (" + a[i][j]
                            + ") is greater than its Max (" + mx[i][j] + ").";
                }
            }
        }
        for (int j = 0; j < t.length; j++) {
            int sum = 0;
            for (int[] row : a) {
                sum += row[j];
            }
            if (t[j] < sum) {
                return "Total of R" + j + " (" + t[j] + ") is less than the sum of its Allocation column (" + sum + ").";
            }
        }
        return null;
    }

    static void loadExample() {
        loadSample();
        System.out.println("Loaded the textbook example (5 processes, 3 resources).");
        showTables();
    }

    static int readInt(String prompt, int lo, int hi) {
        while (true) {
            int[] v = readVector(prompt, 1);
            if (v[0] >= lo && v[0] <= hi) {
                return v[0];
            }
            System.out.println("  Please enter a number from " + lo + " to " + hi + ".");
        }
    }

    static int[] readVector(String prompt, int count) {
        while (true) {
            System.out.print(prompt);
            if (!in.hasNextLine()) {
                System.out.println();
                System.exit(0);
            }
            String[] parts = in.nextLine().trim().split("\\s+");
            if (parts.length != count || parts[0].isEmpty()) {
                System.out.println("  Please enter exactly " + count + " number(s), separated by spaces.");
                continue;
            }
            int[] result = new int[count];
            boolean ok = true;
            for (int k = 0; k < count && ok; k++) {
                try {
                    result[k] = Integer.parseInt(parts[k]);
                    if (result[k] < 0) {
                        System.out.println("  '" + parts[k] + "' is negative. Use 0 or more.");
                        ok = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  '" + parts[k] + "' is not a whole number.");
                    ok = false;
                }
            }
            if (ok) {
                return result;
            }
        }
    }

    static boolean safety(int[][] a, boolean print) {
        int[][] need = needOf(a);
        int[] work = availableOf(a);
        boolean[] finish = new boolean[n];
        StringBuilder sequence = new StringBuilder();
        int done = 0;

        if (print) {
            title("SAFETY ALGORITHM");
            System.out.println("Work = Available = " + Arrays.toString(work));
        }

        while (done < n) {
            int pick = -1;
            for (int i = 0; i < n && pick == -1; i++) {
                if (finish[i]) {
                    continue;
                }
                boolean fits = fits(need[i], work);
                if (print) {
                    System.out.println("  " + "P" + i + ": Need " + Arrays.toString(need[i])
                            + " <= Work " + Arrays.toString(work) + " ?  " + (fits ? "YES" : "NO"));
                }
                if (fits) {
                    pick = i;
                }
            }
            if (pick == -1) {
                break;
            }

            for (int j = 0; j < m; j++) {
                work[j] += a[pick][j];
            }
            finish[pick] = true;
            done++;
            sequence.append(sequence.length() == 0 ? "P" : ", P").append(pick);
            if (print) {
                System.out.println("  -> P" + pick + " finishes. Work = " + Arrays.toString(work));
            }
        }

        boolean safe = done == n;
        if (print) {
            System.out.println("\nFinish = " + Arrays.toString(finish));
            if (safe) {
                System.out.println("RESULT: SAFE STATE.  Safe sequence: <" + sequence + ">");
            } else {
                System.out.println("RESULT: UNSAFE STATE. No safe sequence exists (deadlock is possible).");
            }
        }
        return safe;
    }

    static void request() {
        int p = readInt("Which process is requesting (0 to " + (n - 1) + ")? ", 0, n - 1);
        int[] req = readVector("Request vector (" + m + " value(s)): ", m);
        int[][] need = needOf(alloc);
        int[] avail = availableOf(alloc);

        title("REQUEST CHECK: P" + p + " requests " + Arrays.toString(req));

        boolean ok = fits(req, need[p]);
        System.out.println("Step 1: Request <= Need " + Arrays.toString(need[p]) + " ?  " + (ok ? "YES" : "NO"));
        if (!ok) {
            System.out.println("DECISION: ERROR - the process asked for more than its maximum claim.");
            return;
        }

        ok = fits(req, avail);
        System.out.println("Step 2: Request <= Available " + Arrays.toString(avail) + " ?  " + (ok ? "YES" : "NO"));
        if (!ok) {
            System.out.println("DECISION: WAIT - the resources are not available.");
            return;
        }

        int[][] tentative = new int[n][];
        for (int i = 0; i < n; i++) {
            tentative[i] = alloc[i].clone();
        }
        for (int j = 0; j < m; j++) {
            tentative[p][j] += req[j];
        }
        System.out.println("Step 3: Pretend to grant it:");
        System.out.println("        Available      = " + Arrays.toString(availableOf(tentative)));
        System.out.println("        Allocation[P" + p + "] = " + Arrays.toString(tentative[p]));
        System.out.println("        Need[P" + p + "]       = " + Arrays.toString(needOf(tentative)[p]));

        System.out.println("Step 4: Is that state safe?");
        boolean safe = safety(tentative, true);

        if (safe) {
            alloc = tentative;
            System.out.println("\n" + "DECISION: GRANT - the new allocation is kept.");
        } else {
            System.out.println("\n" + "DECISION: WAIT - granting would be unsafe, so it is rolled back.");
        }
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println(rule(56, '='));
        System.out.println("  BANKER'S ALGORITHM  |  Deadlock Avoidance Simulator");
        System.out.println(rule(56, '='));
        while (true) {
            System.out.println();
            System.out.println(rule(56, '-'));
            System.out.println("                      MAIN   MENU");
            System.out.println(rule(56, '-'));
            System.out.println("  1  Enter a system");
            System.out.println("  2  Load textbook example");
            System.out.println("  3  Safety check");
            System.out.println("  4  Resource request");
            System.out.println("  5  Show tables");
            System.out.println("  0  Exit");
            System.out.println(rule(56, '-'));
            int choice = readInt("Choose: ", 0, 5);

            if (choice == 0) {
                System.out.println("Goodbye.");
                return;
            }
            if (choice == 1) {
                enterSystem();
            } else if (choice == 2) {
                loadExample();
            } else if (!loaded) {
                System.out.println("Load or enter a system first (option 1 or 2).");
            } else if (choice == 3) {
                showTables();
                safety(alloc, true);
            } else if (choice == 4) {
                request();
            } else if (choice == 5) {
                showTables();
            }
        }
    }
}