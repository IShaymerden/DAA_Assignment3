
package algorithms;

import graph.Graph;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DagShortestPath {

    public static class Result {
        public final long[] distance;
        public final int[] parent;
        public final long edgeScans;
        public final long sumDistances;

        public Result(long[] distance, int[] parent,
                      long edgeScans, long sumDistances) {
            this.distance = distance;
            this.parent = parent;
            this.edgeScans = edgeScans;
            this.sumDistances = sumDistances;
        }

        public List<Integer> getPath(int target) {
            if (target < 0 || target >= distance.length) {
                throw new IllegalArgumentException("Invalid vertex");
            }

            List<Integer> path = new ArrayList<>();
            int current = target;

            while (current != -1) {
                path.add(current);
                current = parent[current];
            }

            Collections.reverse(path);
            return path;
        }
    }

    public static Result run(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "DAG shortest paths require a directed graph");
        }

        Kahn.Result topology = Kahn.run(graph);

        if (topology.hasCycle) {
            throw new IllegalArgumentException(
                    "Graph contains a cycle");
        }

        int n = graph.vertexCount();
        long INF = Long.MAX_VALUE / 4;

        long[] distance = new long[n];
        int[] parent = new int[n];
        int[] indegree = new int[n];

        Arrays.fill(distance, INF);
        Arrays.fill(parent, -1);

        for (Graph.Edge edge : graph.getEdges()) {
            indegree[edge.to]++;
        }

        // Virtual super-source:
        // Every vertex with in-degree 0 starts at 0
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                distance[i] = 0;
            }
        }

        long edgeScans = 0;

        // Relax edges in topological order
        for (int u : topology.order) {
            for (Graph.Edge edge : graph.neighbors(u)) {
                edgeScans++;

                if (distance[u] != INF) {
                    long newDistance =
                            distance[u] + edge.weight;

                    if (newDistance < distance[edge.to]) {
                        distance[edge.to] = newDistance;
                        parent[edge.to] = u;
                    }
                }
            }
        }

        long sumDistances = 0;

        for (long d : distance) {
            if (d != INF) {
                sumDistances += d;
            }
        }

        return new Result(
                distance, parent, edgeScans, sumDistances
        );
    }
}
