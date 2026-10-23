
package algorithms;

import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DagShortestPathTest {

    private long[] bellmanFordReference(Graph graph) {
        int n = graph.vertexCount();
        long inf = Long.MAX_VALUE / 4;

        long[] dist = new long[n];
        int[] indegree = new int[n];

        Arrays.fill(dist, inf);

        for (Graph.Edge edge : graph.getEdges()) {
            indegree[edge.to]++;
        }

        // Every vertex with in-degree 0 is a source
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                dist[i] = 0;
            }
        }

        // Bellman-Ford relaxation
        for (int i = 0; i < n - 1; i++) {
            boolean changed = false;

            for (Graph.Edge edge : graph.getEdges()) {
                if (dist[edge.from] != inf
                        && dist[edge.from] + edge.weight
                        < dist[edge.to]) {

                    dist[edge.to] =
                            dist[edge.from] + edge.weight;

                    changed = true;
                }
            }

            if (!changed) {
                break;
            }
        }

        return dist;
    }

    @Test
    void testNegativeWeights() {
        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 3, -10);
        graph.addEdge(2, 3, 2);

        DagShortestPath.Result result =
                DagShortestPath.run(graph);

        assertArrayEquals(
                new long[]{0, 5, 3, -5},
                result.distance
        );

        assertEquals(3, result.sumDistances);
        assertEquals(4, result.edgeScans);

        assertEquals(
                List.of(0, 1, 3),
                result.getPath(3)
        );
    }

    @Test
    void testRandomDagsAgainstBellmanFord() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            int n = 2 + random.nextInt(30);
            Graph graph = new Graph(n, true);

            // Edges from lower to higher vertices
            // guarantee that the graph is a DAG
            for (int u = 0; u < n; u++) {
                for (int v = u + 1; v < n; v++) {

                    if (random.nextDouble() < 0.2) {
                        int weight =
                                random.nextInt(41) - 20;

                        graph.addEdge(u, v, weight);
                    }
                }
            }

            long[] expected =
                    bellmanFordReference(graph);

            DagShortestPath.Result actual =
                    DagShortestPath.run(graph);

            assertArrayEquals(expected, actual.distance);

            assertEquals(
                    graph.edgeCount(),
                    actual.edgeScans
            );
        }
    }

    @Test
    void testCycleRejected() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        assertThrows(
                IllegalArgumentException.class,
                () -> DagShortestPath.run(graph)
        );
    }

    @Test
    void testDisconnectedDag() {
        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 4);
        graph.addEdge(2, 3, 7);

        DagShortestPath.Result result =
                DagShortestPath.run(graph);

        assertArrayEquals(
                new long[]{0, 4, 0, 7, 0},
                result.distance
        );

        assertEquals(11, result.sumDistances);
        assertEquals(2, result.edgeScans);

        assertEquals(List.of(2, 3), result.getPath(3));
        assertEquals(List.of(4), result.getPath(4));
    }
}

