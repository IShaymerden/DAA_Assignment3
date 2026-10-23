package algorithms;

import graph.Graph;
import graph.GraphReader;

public class KosarajuTestRun {

    public static void main(String[] args) throws Exception {

        Graph graph = GraphReader.readFromFile("data/cyclic.txt");

        Kosaraju.Result result = Kosaraju.run(graph);

        System.out.println("SCC count: " + result.componentCount);
        System.out.println("Edge scans: " + result.edgeScans);
        System.out.println("Max stack depth: " + result.maxDepth);

        for (int i = 0; i < graph.vertexCount(); i++) {
            System.out.println(
                    "Vertex " + i + " -> Component "
                            + result.componentOf[i]
            );
        }

        if (result.componentCount != 2) {
            throw new AssertionError("Incorrect SCC count");
        }

        if (result.edgeScans != 2L * graph.edgeCount()) {
            throw new AssertionError("Incorrect edge scan count");
        }

        System.out.println("Kosaraju test: PASS");
    }
}

