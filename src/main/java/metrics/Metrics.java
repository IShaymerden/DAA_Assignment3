
package metrics;

public class Metrics {

    public long dsuOps;
    public long edgeScans;
    public long comparisons;
    public long maxDepth;

    public Metrics() {
        reset();
    }

    public void reset() {
        dsuOps = 0;
        edgeScans = 0;
        comparisons = 0;
        maxDepth = 0;
    }
}
