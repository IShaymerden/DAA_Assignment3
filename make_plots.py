
import csv
import math
from pathlib import Path

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

CSV = Path("results/results.csv")
OUT = Path("results/plots")
OUT.mkdir(parents=True, exist_ok=True)

with CSV.open(encoding="utf-8") as f:
    rows = list(csv.DictReader(f))

if len(rows) != 50:
    raise ValueError(f"Expected 50 rows, found {len(rows)}")

for row in rows:
    for field in ("n", "m", "dsu_ops", "edge_scans",
                  "comparisons", "max_depth"):
        row[field] = int(row[field])

algorithms = [
    "Kruskal", "Prim", "Kosaraju",
    "Kahn", "DagShortestPath"
]

# Plot 1: operations versus n
plt.figure(figsize=(11, 7))

for alg in algorithms:
    for density in (4, 20):
        points = sorted(
            [r for r in rows
             if r["algorithm"] == alg
             and r["m"] == density * r["n"]],
            key=lambda r: r["n"]
        )

        x = [r["n"] for r in points]
        y = [
            r["dsu_ops"] + r["edge_scans"]
            + r["comparisons"]
            for r in points
        ]

        plt.plot(x, y, marker="o",
                 label=f"{alg} {density}n")

plt.xscale("log")
plt.yscale("log")
plt.xlabel("Vertices (n)")
plt.ylabel("Counted operations")
plt.title("Operations vs Graph Size")
plt.legend(fontsize=8)
plt.grid(True, which="both", alpha=0.3)
plt.tight_layout()
plt.savefig(OUT / "operations.png", dpi=180)
plt.close()

# Plot 2: scans per edge
plt.figure(figsize=(10, 6))

for alg in algorithms:
    for density in (4, 20):
        points = sorted(
            [r for r in rows
             if r["algorithm"] == alg
             and r["m"] == density * r["n"]],
            key=lambda r: r["n"]
        )

        x = [r["n"] for r in points]
        y = [r["edge_scans"] / r["m"] for r in points]

        plt.plot(x, y, marker="o",
                 label=f"{alg} {density}n")

plt.xscale("log")
plt.xlabel("Vertices (n)")
plt.ylabel("Edge scans / m")
plt.title("Scans per Edge")
plt.legend(fontsize=8)
plt.grid(True, alpha=0.3)
plt.tight_layout()
plt.savefig(OUT / "scans_per_edge.png", dpi=180)
plt.close()

# Plot 3: Kruskal scanned-edge share
plt.figure(figsize=(9, 6))

for density in (4, 20):
    points = sorted(
        [r for r in rows
         if r["algorithm"] == "Kruskal"
         and r["m"] == density * r["n"]],
        key=lambda r: r["n"]
    )

    plt.plot(
        [r["n"] for r in points],
        [r["edge_scans"] / r["m"] for r in points],
        marker="o",
        label=f"Density {density}n"
    )

plt.xscale("log")
plt.xlabel("Vertices (n)")
plt.ylabel("Scanned edges / m")
plt.title("Kruskal: Share of Edges Examined")
plt.legend()
plt.grid(True, alpha=0.3)
plt.tight_layout()
plt.savefig(OUT / "kruskal_edges.png", dpi=180)
plt.close()

def slope(algorithm, density):
    points = sorted(
        [r for r in rows
         if r["algorithm"] == algorithm
         and r["m"] == density * r["n"]],
        key=lambda r: r["n"]
    )
    x = [math.log(r["n"]) for r in points]
    y = [math.log(r["edge_scans"]) for r in points]

    mx = sum(x) / len(x)
    my = sum(y) / len(y)

    return sum((a-mx)*(b-my) for a, b in zip(x, y)) / \
           sum((a-mx)**2 for a in x)

print("Generated 3 plots.")
for algorithm in ["Kruskal", "Kosaraju", "Kahn", "DagShortestPath"]:
    for density in [4, 20]:
        print(f"{algorithm} {density}n slope: "
              f"{slope(algorithm, density):.3f}")
