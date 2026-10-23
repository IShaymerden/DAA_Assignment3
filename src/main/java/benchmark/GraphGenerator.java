
package benchmark;

import graph.Graph;
import java.util.Random;

public class GraphGenerator {

    private static final int SEED = 42;

    private static int[] shuffled(int n, Random rnd) {
        int[] permutation = new int[n];

        for (int i = 0; i < n; i++) {
            permutation[i] = i;
        }

        for (int i = n - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);

            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }

        return permutation;
    }

    public static Graph undirected(int n, int m) {
        if (n < 1 || m < n - 1) {
            throw new IllegalArgumentException("Invalid graph size");
        }

        Random rnd = new Random(SEED);
        Graph graph = new Graph(n, false);

        int[] perm = shuffled(n, rnd);

        for (int i = 1; i < n; i++) {
            int a = perm[rnd.nextInt(i)];
            int b = perm[i];

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        for (int i = n - 1; i < m; i++) {
            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    public static Graph directed(int n, int m) {
        if (n < 1 || m < 0) {
            throw new IllegalArgumentException("Invalid graph size");
        }

        Random rnd = new Random(SEED);
        Graph graph = new Graph(n, true);

        for (int i = 0; i < m; i++) {
            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }

    public static Graph dag(int n, int m) {
        if (n < 2 || m < 0) {
            throw new IllegalArgumentException("Invalid graph size");
        }

        Random rnd = new Random(SEED);
        Graph graph = new Graph(n, true);

        int[] permutation = shuffled(n, rnd);
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[permutation[i]] = i;
        }

        for (int i = 0; i < m; i++) {
            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);

            while (b == a) {
                b = rnd.nextInt(n);
            }

            if (rank[a] > rank[b]) {
                int temp = a;
                a = b;
                b = temp;
            }

            int weight = 1 + rnd.nextInt(1000);

            graph.addEdge(a, b, weight);
        }

        return graph;
    }
}

