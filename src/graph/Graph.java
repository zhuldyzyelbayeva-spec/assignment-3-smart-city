
package graph;

import java.util.*;

public class Graph {

 public static class Edge {

  public final int from, to, weight;

  public Edge(int a, int b, int w) {
   from = a;
   to = b;
   weight = w;
  }
 }

 public final int n;
 public final boolean directed;

 public final List<Edge> edges = new ArrayList<>();
 public final List<List<Edge>> adj = new ArrayList<>();

 public Graph(int n, boolean directed) {
  if (n < 1) {
   throw new IllegalArgumentException();
  }

  this.n = n;
  this.directed = directed;

  for (int i = 0; i < n; i++) {
   adj.add(new ArrayList<>());
  }
 }

 public void add(int a, int b, int w) {
  if (a < 0 || b < 0 || a >= n || b >= n || w < 0) {
   throw new IllegalArgumentException(
           "Invalid vertex or negative weight"
   );
  }

  Edge e = new Edge(a, b, w);

  edges.add(e);
  adj.get(a).add(e);

  if (!directed) {
   adj.get(b).add(new Edge(b, a, w));
  }
 }
}
