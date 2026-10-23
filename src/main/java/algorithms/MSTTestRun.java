package algorithms;

import graph.Graph;
import graph.GraphReader;

public class MSTTestRun {

    public static void main(String[] args) throws Exception {

        String[] files = {
                "data/connected.txt",
                "data/disconnected.txt"
        };

        for (String file : files) {
            Graph graph = GraphReader.readFromFile(file);

            Kruskal.Result kruskal = Kruskal.run(graph);
            Prim.Result prim = Prim.run(graph);

            System.out.println("Graph: " + file);
            System.out.println("Kruskal weight: " + kruskal.totalWeight);
            System.out.println("Prim weight: " + prim.totalWeight);
            System.out.println("Kruskal components: " + kruskal.components);
            System.out.println("Prim components: " + prim.components);

            if (kruskal.totalWeight != prim.totalWeight
                    || kruskal.components != prim.components) {
                throw new AssertionError(
                        "Kruskal and Prim results do not match!");
            }

            System.out.println("MST agreement: PASS");
            System.out.println();
        }
    }
}
