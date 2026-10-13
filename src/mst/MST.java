
package mst;

import graph.Graph;
import dsu.DSU;
import java.util.*;

public class MST {

 public static class Result {

  public long weight, operations;
  public boolean connected;

  Result(long w, long op, boolean c) {
   weight = w;
   operations = op;
   connected = c;
  }
 }

 public static Result kruskal(Graph g) {
  if (g.directed) {
   throw new IllegalArgumentException(
           "Undirected graph required"
   );
  }

  List<Graph.Edge> e = new ArrayList<>(g.edges);

  e.sort(Comparator.comparingInt(x -> x.weight));

  DSU d = new DSU(g.n);

  long weight = 0, examined = 0;
  int count = 0;

  for (Graph.Edge x : e) {
   examined++;

   if (d.union(x.from, x.to)) {
    weight += x.weight;
    count++;

    if (count == g.n - 1) {
     break;
    }
   }
  }

  return new Result(
          weight,
          examined + d.operations,
          count == g.n - 1
  );
 }

 public static Result prim(Graph g) {
  if (g.directed) {
   throw new IllegalArgumentException(
           "Undirected graph required"
   );
  }

  boolean[] seen = new boolean[g.n];

  PriorityQueue<Graph.Edge> pq = new PriorityQueue<>(
          Comparator.comparingInt(e -> e.weight)
  );

  pq.add(new Graph.Edge(-1, 0, 0));

  long weight = 0, ops = 0;
  int count = 0;

  while (!pq.isEmpty()) {
   Graph.Edge e = pq.poll();
   ops++;

   if (seen[e.to]) {
    continue;
   }

   seen[e.to] = true;
   count++;
   weight += e.weight;

   for (Graph.Edge next : g.adj.get(e.to)) {
    ops++;

    if (!seen[next.to]) {
     pq.add(next);
    }
   }
  }

  return new Result(
          weight,
          ops,
          count == g.n
  );
 }
}
