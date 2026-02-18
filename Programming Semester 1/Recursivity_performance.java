import java.util.HashMap;

public class Recursivity_performance {

    // --- your memo map ---
    private static HashMap<Integer, Integer> memo = new HashMap<>();

    // --- "blackhole" so the JVM can't optimize away work ---
    private static volatile int blackhole;

    // ---------------- YOUR FUNCTIONS ----------------

    static int factorial(int n) {
        if (n == 0) return 1;
        return n * factorial(n - 1);
    }

    static int fibonacciRecursive(int n) {
        if (n <= 1) return n;
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    static int fibonacciIterative(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;

        int a = 0, b = 1, sum;
        for (int i = 2; i <= n; i++) {
            sum = a + b;
            a = b;
            b = sum;
        }
        return b;
    }

    static int fibonacciMemo(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;

        Integer cached = memo.get(n);
        if (cached != null) return cached;

        int result = fibonacciMemo(n - 1) + fibonacciMemo(n - 2);
        memo.put(n, result);
        return result;
    }

    // ---------------- TIMING HELPERS ----------------

    /**
     * Times a function call repeated `reps` times.
     * Returns average time per call in nanoseconds.
     */
    static long timeAverageNs(IntFunction f, int n, int reps, boolean clearMemoEachRep) {
        // Warm-up (helps stabilize timings due to JIT compilation)
        for (int i = 0; i < 5_000; i++) {
            if (clearMemoEachRep) memo.clear();
            blackhole ^= f.apply(n);
        }

        long total = 0;
        for (int i = 0; i < reps; i++) {
            if (clearMemoEachRep) memo.clear();

            long start = System.nanoTime();
            int result = f.apply(n);
            long end = System.nanoTime();

            blackhole ^= result; // consume result
            total += (end - start);
        }
        return total / reps;
    }

    // Simple functional interface (so we can pass methods around)
    interface IntFunction {
        int apply(int n);
    }

    // ---------------- MAIN EXPERIMENT ----------------

    public static void main(String[] args) {

        // Choose n values to test
        int[] ns = {5, 10, 15, 20, 25, 30, 35, 40};

        // Repetitions per measurement
        // For very fast functions use more reps; for slow recursive fib use fewer.
        int repsFast = 50_000;
        int repsSlow = 20;

        System.out.println("Timing results (average nanoseconds per call)");
        System.out.println("n, factorial(ns), fib_recursive(ns), fib_iterative(ns), fib_memo(ns)");

        for (int n : ns) {
            long tFact = timeAverageNs(BenchmarkProgram::factorial, Math.min(n, 12), repsFast, false);
            // factorial grows fast in value; we cap input to 12 to avoid int overflow (12! fits in int)

            long tRec;
            if (n <= 40) {
                tRec = timeAverageNs(BenchmarkProgram::fibonacciRecursive, n, repsSlow, false);
            } else {
                tRec = -1; // not used here, but kept for structure
            }

            long tIter = timeAverageNs(BenchmarkProgram::fibonacciIterative, n, repsFast, false);

            // For memo version: clear memo EACH repetition so each call measures the real memo-building work
            long tMemo = timeAverageNs(BenchmarkProgram::fibonacciMemo, n, repsFast, true);

            System.out.printf("%d, %d, %d, %d, %d%n", n, tFact, tRec, tIter, tMemo);
        }

        // Print blackhole so JVM can't prove it's unused
        System.err.println("ignore: " + blackhole);
    }
}
