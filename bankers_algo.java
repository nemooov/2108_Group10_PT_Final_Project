import java.util.Arrays;

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
        System.out.println("\nTotal     = " + Arrays.toString(total));
        System.out.println("Available = " + Arrays.toString(availableOf(alloc)));
        System.out.println();
        System.out.printf("%-5s %-14s %-14s %-14s%n", "", "Allocation", "Max", "Need");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-5s %-14s %-14s %-14s%n", "P" + i,
                    Arrays.toString(alloc[i]), Arrays.toString(max[i]), Arrays.toString(need[i]));
        }
    }

    public static void main(String[] args) {
        loadSample();
        showTables();
    }
}