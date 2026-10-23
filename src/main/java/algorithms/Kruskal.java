package algorithms;

import graph.Graph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    public static class Result {
        public final long totalWeight;
        public final List<Graph.Edge> mstEdges;
        public final int components;
        public final long edgeScans;
        public final long dsuOps;
        public final long comparisons;

        public Result(long totalWeight, List<Graph.Edge> mstEdges,
                      int components, long edgeScans,
                      long dsuOps, long comparisons) {
            this.totalWeight = totalWeight;
            this.mstEdges = mstEdges;
            this.components = components;
            this.edgeScans = edgeScans;
            this.dsuOps = dsuOps;
            this.comparisons = comparisons;
        }
    }

    public static Result run(Graph graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Kruskal requires an undirected graph");
        }

        List<Graph.Edge> edges =
                new ArrayList<>(graph.getEdges());

        final long[] comparisons = {0};

        edges.sort((a, b) -> {
            comparisons[0]++;
            return Integer.compare(a.weight, b.weight);
        });

        DSU dsu = new DSU(graph.vertexCount());
        List<Graph.Edge> mstEdges = new ArrayList<>();

        long totalWeight = 0;
        long edgeScans = 0;

        for (Graph.Edge edge : edges) {
            edgeScans++;

            if (dsu.union(edge.from, edge.to)) {
                mstEdges.add(edge);
                totalWeight += edge.weight;
            }

            if (dsu.components() == 1) {
                break;
            }
        }

        return new Result(
                totalWeight,
                mstEdges,
                dsu.components(),
                edgeScans,
                dsu.getDsuOps(),
                comparisons[0]
        );
    }
}
