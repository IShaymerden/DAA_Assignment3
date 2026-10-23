package algorithms;

public class DSU {

    private final int[] parent;
    private final int[] size;

    private int componentCount;
    private long findCalls;
    private long successfulUnions;

    public DSU(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Invalid size");
        }

        parent = new int[n];
        size = new int[n];
        makeSet(n);
    }

    public void makeSet(int n) {
        if (n != parent.length) {
            throw new IllegalArgumentException("Invalid size");
        }

        componentCount = n;
        findCalls = 0;
        successfulUnions = 0;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        findCalls++;

        // Path compression
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false;
        }

        // Union by size
        if (size[rootX] < size[rootY]) {
            int temp = rootX;
            rootX = rootY;
            rootY = temp;
        }

        parent[rootY] = rootX;
        size[rootX] += size[rootY];

        componentCount--;
        successfulUnions++;

        return true;
    }

    public int components() {
        return componentCount;
    }

    public long getFindCalls() {
        return findCalls;
    }

    public long getSuccessfulUnions() {
        return successfulUnions;
    }

    public long getDsuOps() {
        return findCalls + successfulUnions;
    }
}
