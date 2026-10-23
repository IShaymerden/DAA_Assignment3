package graph;

public class Main {

    public static void main(String[] args) throws Exception {

        Graph graph = GraphReader.readFromFile("data/connected.txt");

        System.out.println("Vertices: " + graph.vertexCount());
        System.out.println("Edges: " + graph.edgeCount());
        System.out.println("Directed: " + graph.isDirected());

        for (int i = 0; i < graph.vertexCount(); i++) {
            System.out.print("Vertex " + i + ": ");

            for (Graph.Edge edge : graph.neighbors(i)) {
                System.out.print(edge.to + "(" + edge.weight + ") ");
            }

            System.out.println();
        }
    }
}
