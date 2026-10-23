
package algorithms;

import graph.Graph;
import graph.GraphReader;

public class KahnTestRun {

    public static void main(String[] args) throws Exception {

        Graph dag = new Graph(5, true);
        dag.addEdge(0, 1, 5);
        dag.addEdge(0, 2, 3);
        dag.addEdge(1, 3, 6);
        dag.addEdge(2, 3, 2);
        dag.addEdge(3, 4, 1);

        Kahn.Result result = Kahn.run(dag);

        System.out.println("DAG order: " + result.order);
        System.out.println("Has cycle: " + result.hasCycle);
        System.out.println("Valid order: "
                + Kahn.isValidOrder(dag, result.order));
        System.out.println("Edge scans: " + result.edgeScans);

        Graph cyclic =
                GraphReader.readFromFile("data/cyclic.txt");

        Kahn.Result cycleResult = Kahn.run(cyclic);

        System.out.println("Cyclic graph detected: "
                + cycleResult.hasCycle);

        if (result.hasCycle
                || !Kahn.isValidOrder(dag, result.order)
                || result.edgeScans != dag.edgeCount()
                || !cycleResult.hasCycle) {
            throw new AssertionError("Kahn test failed");
        }

        System.out.println("Kahn test: PASS");
    }
}
