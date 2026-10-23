
package algorithms;

import graph.Graph;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Kahn {

    public static class Result {
        public final List<Integer> order;
        public final boolean hasCycle;
        public final long edgeScans;

        public Result(List<Integer> order, boolean hasCycle,
                      long edgeScans) {
            this.order = order;
            this.hasCycle = hasCycle;
            this.edgeScans = edgeScans;
        }
    }

    public static Result run(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Kahn requires a directed graph");
        }

        int n = graph.vertexCount();
        int[] indegree = new int[n];

        // Calculate in-degrees
        for (Graph.Edge edge : graph.getEdges()) {
            indegree[edge.to]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        long edgeScans = 0;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            order.add(vertex);

            for (Graph.Edge edge : graph.neighbors(vertex)) {
                edgeScans++;
                indegree[edge.to]--;

                if (indegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }

        boolean hasCycle = order.size() != n;

        return new Result(order, hasCycle, edgeScans);
    }

    public static boolean isValidOrder(
            Graph graph, List<Integer> order) {

        int n = graph.vertexCount();

        if (order.size() != n) {
            return false;
        }

        int[] position = new int[n];
        boolean[] seen = new boolean[n];

        for (int i = 0; i < n; i++) {
            int vertex = order.get(i);

            if (vertex < 0 || vertex >= n || seen[vertex]) {
                return false;
            }

            seen[vertex] = true;
            position[vertex] = i;
        }

        for (Graph.Edge edge : graph.getEdges()) {
            if (position[edge.from] >= position[edge.to]) {
                return false;
            }
        }

        return true;
    }
}
