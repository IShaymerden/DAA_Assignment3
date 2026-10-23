package algorithms;

import graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Kosaraju {

    public static class Result {
        public final int componentCount;
        public final int[] componentOf;
        public final long edgeScans;
        public final int maxDepth;

        public Result(int componentCount, int[] componentOf,
                      long edgeScans, int maxDepth) {
            this.componentCount = componentCount;
            this.componentOf = componentOf;
            this.edgeScans = edgeScans;
            this.maxDepth = maxDepth;
        }
    }

    private static class Frame {
        int vertex;
        int nextIndex;

        Frame(int vertex) {
            this.vertex = vertex;
            this.nextIndex = 0;
        }
    }

    public static Result run(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Kosaraju requires a directed graph");
        }

        int n = graph.vertexCount();
        boolean[] visited = new boolean[n];
        List<Integer> finishOrder = new ArrayList<>(n);

        long edgeScans = 0;
        int maxDepth = 0;

        // First pass: compute finishing order
        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            Deque<Frame> stack = new ArrayDeque<>();
            stack.push(new Frame(start));
            visited[start] = true;

            maxDepth = Math.max(maxDepth, stack.size());

            while (!stack.isEmpty()) {
                Frame frame = stack.peek();
                List<Graph.Edge> neighbors =
                        graph.neighbors(frame.vertex);

                if (frame.nextIndex < neighbors.size()) {
                    Graph.Edge edge =
                            neighbors.get(frame.nextIndex++);

                    edgeScans++;

                    if (!visited[edge.to]) {
                        visited[edge.to] = true;
                        stack.push(new Frame(edge.to));

                        maxDepth = Math.max(
                                maxDepth, stack.size());
                    }
                } else {
                    finishOrder.add(frame.vertex);
                    stack.pop();
                }
            }
        }

        // Reverse the graph
        List<List<Integer>> reversed = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            reversed.add(new ArrayList<>());
        }

        for (Graph.Edge edge : graph.getEdges()) {
            reversed.get(edge.to).add(edge.from);
        }

        // Second pass: discover components
        Arrays.fill(visited, false);

        int[] componentOf = new int[n];
        int componentCount = 0;

        for (int i = finishOrder.size() - 1; i >= 0; i--) {
            int start = finishOrder.get(i);

            if (visited[start]) {
                continue;
            }

            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(start);
            visited[start] = true;

            maxDepth = Math.max(maxDepth, stack.size());

            while (!stack.isEmpty()) {
                int vertex = stack.pop();
                componentOf[vertex] = componentCount;

                for (int next : reversed.get(vertex)) {
                    edgeScans++;

                    if (!visited[next]) {
                        visited[next] = true;
                        stack.push(next);

                        maxDepth = Math.max(
                                maxDepth, stack.size());
                    }
                }
            }

            componentCount++;
        }

        return new Result(
                componentCount,
                componentOf,
                edgeScans,
                maxDepth
        );
    }
}
