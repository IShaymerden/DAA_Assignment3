# DAA Assignment 3 — Graph Algorithms

**Student:** Iskander Shaymerdenov  
**University:** Astana IT University  
**Course:** Design and Analysis of Algorithms  
**Language:** Java 17  
**Build System:** Maven  
**Testing:** JUnit 5

## Project Overview

This project implements six graph algorithms for the City Network & Job Scheduler scenario.

The system supports weighted directed and undirected graphs using adjacency lists. It includes correctness testing, operation counters, reproducible benchmarking, and performance visualization.

## Implemented Algorithms

| Algorithm | Description | Time Complexity |
|---|---|---|
| DSU | Disjoint sets with path compression and union by size | O(α(V)) amortized |
| Kruskal | Minimum spanning forest using DSU | O(E log E) |
| Prim | Minimum spanning forest using a priority queue | O(E log V) |
| Kosaraju | Strongly connected components using iterative DFS | O(V + E) |
| Kahn | Topological sorting and cycle detection | O(V + E) |
| DAG Shortest Paths | Shortest paths with negative edge weights supported | O(V + E) |

## Project Structure

```text
DAA_Assignment3/
├── pom.xml
├── README.md
├── REPORT.md
├── make_plots.py
├── data/
│   ├── connected.txt
│   ├── disconnected.txt
│   └── cyclic.txt
├── src/
│   ├── main/java/
│   │   ├── graph/
│   │   ├── algorithms/
│   │   ├── metrics/
│   │   └── benchmark/
│   └── test/java/
│       └── algorithms/
└── results/
    ├── results.csv
    └── plots/
        ├── operations.png
        ├── scans_per_edge.png
        └── kruskal_edges.png
```

## Requirements

- Java Development Kit 17 or newer
- Apache Maven
- Python 3 with Matplotlib (for plots)

## Build and Testing

To compile the project:

```bash
mvn clean compile
```

To run all automated tests:

```bash
mvn test
```

Tests cover random connected graphs, disconnected graphs, self-loops, parallel edges, strongly connected components, topological ordering, cycle detection, negative weights, and stack safety on 100,000 vertices.

## Run Benchmark

First compile the project:

```bash
mvn compile
```

Then run:

```bash
java -cp target/classes benchmark.BenchmarkRunner
```

The benchmark uses five graph sizes, two densities, and five measured runs per algorithm.

It writes 50 rows of results to:

`results/results.csv`

The benchmark checks MST agreement, topological ordering, and reference values.

## Generate Plots

Install Matplotlib:

```bash
python -m pip install matplotlib
```

Run:

```bash
python make_plots.py
```

Three PNG figures will be generated in `results/plots/`.

## Input Format

The graph input format is:

```text
n m directed|undirected
u v weight
u v weight
...
```

Vertices are numbered from 0 to n-1.

## Git Workflow

Development branches:

- `feature/dsu`
- `feature/mst`
- `feature/scc`
- `feature/paths`
- `feature/metrics`

The `main` branch contains the completed project. The final release is marked with tag `v1.0`.

## AI Use Acknowledgement

AI assistance was used to support project planning, code development, explanations, debugging, testing, and documentation. The implementation was integrated and executed in IntelliJ IDEA, with results checked against the assignment requirements.

## Report

The complexity analysis, correctness explanations, experimental discussion, algorithm selection guidelines, and figures are available in [REPORT.md](REPORT.md).