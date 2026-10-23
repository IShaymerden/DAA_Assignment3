package algorithms;

import graph.Graph;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    @Test
    void test100RandomConnectedGraphs() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            int n = 2 + random.nextInt(49);
            Graph graph = new Graph(n, false);

            // Create a spanning tree first
            for (int i = 1; i < n; i++) {
                int parent = random.nextInt(i);
                int weight = 1 + random.nextInt(1000);
                graph.addEdge(parent, i, weight);
            }

            // Add random extra edges
            int extraEdges = n * 3;

            for (int i = 0; i < extraEdges; i++) {
                int u = random.nextInt(n);
                int v = random.nextInt(n);

                if (u != v) {
                    int weight = 1 + random.nextInt(1000);
                    graph.addEdge(u, v, weight);
                }
            }

            Kruskal.Result kruskal = Kruskal.run(graph);
            Prim.Result prim = Prim.run(graph);

            assertEquals(kruskal.totalWeight, prim.totalWeight);
            assertEquals(n - 1, kruskal.mstEdges.size());
            assertEquals(n - 1, prim.mstEdges.size());
            assertEquals(1, kruskal.components);
            assertEquals(1, prim.components);
        }
    }

    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph(5, false);

        graph.addEdge(0, 1, 5);
        graph.addEdge(2, 3, 7);

        Kruskal.Result kruskal = Kruskal.run(graph);
        Prim.Result prim = Prim.run(graph);

        assertEquals(12, kruskal.totalWeight);
        assertEquals(12, prim.totalWeight);
        assertEquals(3, kruskal.components);
        assertEquals(3, prim.components);
        assertEquals(2, kruskal.mstEdges.size());
        assertEquals(2, prim.mstEdges.size());
    }

    @Test
    void testSelfLoopsAndParallelEdges() {
        Graph graph = new Graph(3, false);

        graph.addEdge(0, 0, -100);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 1, 3);
        graph.addEdge(1, 2, 5);

        Kruskal.Result kruskal = Kruskal.run(graph);
        Prim.Result prim = Prim.run(graph);

        assertEquals(8, kruskal.totalWeight);
        assertEquals(8, prim.totalWeight);
        assertEquals(2, kruskal.mstEdges.size());
        assertEquals(2, prim.mstEdges.size());
    }
}
