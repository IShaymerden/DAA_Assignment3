package algorithms;

import graph.Graph;
import graph.GraphReader;

public class KruskalTestRun {

    public static void main(String[] args) throws Exception {

        String[] files = {
                "data/connected.txt",
                "data/disconnected.txt"
        };

        for (String file : files) {
            Graph graph = GraphReader.readFromFile(file);
            Kruskal.Result result = Kruskal.run(graph);

            System.out.println("Graph: " + file);
            System.out.println("Total weight: " + result.totalWeight);
            System.out.println("MST edges: " + result.mstEdges.size());
            System.out.println("Components: " + result.components);
            System.out.println("Edge scans: " + result.edgeScans);
            System.out.println("DSU ops: " + result.dsuOps);
            System.out.println();
        }
    }
}
