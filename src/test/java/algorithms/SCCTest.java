
package algorithms;

import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SCCTest {

    // Reference BFS for checking reachability
    private boolean reachable(Graph graph, int start, int target) {
        boolean[] visited = new boolean[graph.vertexCount()];
        Queue<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            if (u == target) {
                return true;
            }

            for (Graph.Edge edge : graph.neighbors(u)) {
                if (!visited[edge.to]) {
                    visited[edge.to] = true;
                    queue.add(edge.to);
                }
            }
        }

        return false;
    }

    @Test
    void testKosarajuAgainstBruteForce() {
        Random random = new Random(42);

        for (int test = 0; test < 30; test++) {
            int n = 2 + random.nextInt(19);
            Graph graph = new Graph(n, true);

            for (int i = 0; i < n * 3; i++) {
                graph.addEdge(
                        random.nextInt(n),
                        random.nextInt(n),
                        1
                );
            }

            Kosaraju.Result result = Kosaraju.run(graph);

            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    boolean sameComponent =
                            result.componentOf[u] == result.componentOf[v];

                    boolean mutuallyReachable =
                            reachable(graph, u, v)
                                    && reachable(graph, v, u);

                    assertEquals(mutuallyReachable, sameComponent);
                }
            }

            assertEquals(
                    2L * graph.edgeCount(),
                    result.edgeScans
            );
        }
    }

    @Test
    void testKahnValidOrder() {
        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);

        Kahn.Result result = Kahn.run(graph);

        assertFalse(result.hasCycle);
        assertTrue(Kahn.isValidOrder(graph, result.order));
        assertEquals(graph.edgeCount(), result.edgeScans);
    }

    @Test
    void testKahnCycleDetection() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        Kahn.Result result = Kahn.run(graph);

        assertTrue(result.hasCycle);
        assertFalse(Kahn.isValidOrder(graph, result.order));
    }

    @Test
    void test100000VerticesNoStackOverflow() {
        int n = 100_000;
        Graph graph = new Graph(n, true);

        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(i, i + 1, 1);
        }

        Kosaraju.Result result = Kosaraju.run(graph);

        assertEquals(n, result.componentCount);
        assertEquals(2L * (n - 1), result.edgeScans);
        assertTrue(result.maxDepth > 0);
    }
}
