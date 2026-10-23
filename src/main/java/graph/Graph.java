package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static class Edge {
        public final int from;
        public final int to;
        public final int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private final int n;
    private final boolean directed;
    private final List<List<Edge>> adjacencyList;
    private final List<Edge> edges;

    public Graph(int n, boolean directed) {
        if (n < 0) {
            throw new IllegalArgumentException("Invalid vertex count");
        }

        this.n = n;
        this.directed = directed;
        this.adjacencyList = new ArrayList<>();
        this.edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        checkVertex(from);
        checkVertex(to);

        Edge edge = new Edge(from, to, weight);
        edges.add(edge);
        adjacencyList.get(from).add(edge);

        if (!directed && from != to) {
            adjacencyList.get(to).add(
                    new Edge(to, from, weight)
            );
        }
    }

    private void checkVertex(int vertex) {
        if (vertex < 0 || vertex >= n) {
            throw new IllegalArgumentException("Invalid vertex: " + vertex);
        }
    }

    public int vertexCount() {
        return n;
    }

    public int edgeCount() {
        return edges.size();
    }

    public boolean isDirected() {
        return directed;
    }

    public List<Edge> neighbors(int vertex) {
        checkVertex(vertex);
        return List.copyOf(adjacencyList.get(vertex));
    }

    public List<Edge> getEdges() {
        return List.copyOf(edges);
    }
}
