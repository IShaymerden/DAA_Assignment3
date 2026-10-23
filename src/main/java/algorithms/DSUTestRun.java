package algorithms;

public class DSUTestRun {

    public static void main(String[] args) {

        DSU dsu = new DSU(5);

        System.out.println("Initial components: " + dsu.components());

        dsu.union(0, 1);
        dsu.union(1, 2);
        dsu.union(3, 4);

        System.out.println("Components after unions: " + dsu.components());

        System.out.println("0 and 2 connected: "
                + (dsu.find(0) == dsu.find(2)));

        System.out.println("0 and 4 connected: "
                + (dsu.find(0) == dsu.find(4)));

        System.out.println("Find calls: " + dsu.getFindCalls());
        System.out.println("Successful unions: " + dsu.getSuccessfulUnions());
        System.out.println("DSU operations: " + dsu.getDsuOps());
    }
}
