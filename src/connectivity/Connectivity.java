
package connectivity;

import graph.Graph;
import java.util.*;

public class Connectivity {

 public static List<Integer> topological(Graph g) {
  if (!g.directed) {
   throw new IllegalArgumentException();
  }

  int[] deg = new int[g.n];

  for (Graph.Edge e : g.edges) {
   deg[e.to]++;
  }

  ArrayDeque<Integer> q = new ArrayDeque<>();

  for (int i = 0; i < g.n; i++) {
   if (deg[i] == 0) {
    q.add(i);
   }
  }

  List<Integer> order = new ArrayList<>();

  while (!q.isEmpty()) {
   int u = q.remove();
   order.add(u);

   for (Graph.Edge e : g.adj.get(u)) {
    if (--deg[e.to] == 0) {
     q.add(e.to);
    }
   }
  }

  return order.size() == g.n
          ? order
          : Collections.emptyList();
 }

 public static long topologicalOperations(Graph g) {
  return g.n + g.edges.size();
 }

 public static List<List<Integer>> scc(Graph g) {
  if (!g.directed) {
   throw new IllegalArgumentException();
  }

  boolean[] seen = new boolean[g.n];
  List<Integer> order = new ArrayList<>();

  for (int i = 0; i < g.n; i++) {
   if (!seen[i]) {
    finish(g, i, seen, order);
   }
  }

  List<List<Integer>> rev = new ArrayList<>();

  for (int i = 0; i < g.n; i++) {
   rev.add(new ArrayList<>());
  }

  for (Graph.Edge e : g.edges) {
   rev.get(e.to).add(e.from);
  }

  Arrays.fill(seen, false);

  List<List<Integer>> parts = new ArrayList<>();

  for (int i = order.size() - 1; i >= 0; i--) {
   int v = order.get(i);

   if (!seen[v]) {
    List<Integer> part = new ArrayList<>();
    ArrayDeque<Integer> st = new ArrayDeque<>();

    st.push(v);
    seen[v] = true;

    while (!st.isEmpty()) {
     int u = st.pop();
     part.add(u);

     for (int w : rev.get(u)) {
      if (!seen[w]) {
       seen[w] = true;
       st.push(w);
      }
     }
    }

    parts.add(part);
   }
  }

  return parts;
 }

 private static void finish(
         Graph g,
         int start,
         boolean[] seen,
         List<Integer> order
 ) {
  ArrayDeque<int[]> st = new ArrayDeque<>();

  seen[start] = true;
  st.push(new int[]{start, 0});

  while (!st.isEmpty()) {
   int[] f = st.peek();
   int u = f[0];

   if (f[1] < g.adj.get(u).size()) {
    int v = g.adj.get(u).get(f[1]++).to;

    if (!seen[v]) {
     seen[v] = true;
     st.push(new int[]{v, 0});
    }
   } else {
    order.add(u);
    st.pop();
   }
  }
 }

 public static long sccOperations(Graph g) {
  return 2L * (g.n + g.edges.size());
 }
}
