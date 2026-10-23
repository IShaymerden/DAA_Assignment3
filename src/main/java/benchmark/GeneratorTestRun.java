
package benchmark;

import graph.Graph;
import algorithms.Kruskal;
import algorithms.Prim;
import algorithms.Kosaraju;
import algorithms.Kahn;
import algorithms.DagShortestPath;

public class GeneratorTestRun {

    public static void main(String[] args) {

        int n = 1000;
        int m = 4000;

        Graph undirected = GraphGenerator.undirected(n, m);
        Graph directed = GraphGenerator.directed(n, m);
        Graph dag = GraphGenerator.dag(n, m);

        Kruskal.Result kruskal = Kruskal.run(undirected);
        Prim.Result prim = Prim.run(undirected);

        Kosaraju.Result kosaraju = Kosaraju.run(directed);
        Kahn.Result kahn = Kahn.run(dag);

        DagShortestPath.Result paths =
                DagShortestPath.run(dag);

        System.out.println("===== BENCHMARK CHECK =====");

        System.out.println("Vertices: " + n);
        System.out.println("Edges: " + m);

        System.out.println("Kruskal MST: " + kruskal.totalWeight);
        System.out.println("Prim MST: " + prim.totalWeight);

        System.out.println("SCC count: " + kosaraju.componentCount);

        System.out.println("DAG order size: " + kahn.order.size());
        System.out.println("DAG distance sum: " + paths.sumDistances);

        System.out.println();
        System.out.println("===== REFERENCE VALUES =====");
        System.out.println("MST: 147970");
        System.out.println("SCC: 27");
        System.out.println("DAG distances: 396274");

        if (kruskal.totalWeight != prim.totalWeight) {
            throw new AssertionError("MST algorithms disagree");
        }

        if (kahn.hasCycle || !Kahn.isValidOrder(dag, kahn.order)) {
            throw new AssertionError("Invalid DAG order");
        }

        if (kosaraju.edgeScans != 2L * m) {
            throw new AssertionError("Invalid SCC scan count");
        }

        if (kahn.edgeScans != m || paths.edgeScans != m) {
            throw new AssertionError("Invalid DAG scan count");
        }

        System.out.println();
        System.out.println("Internal checks: PASS");
    }
}

