import csv,collections,matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
rows=list(csv.DictReader(open('results/tables/benchmark.csv')))
for axis,file in [('V','time_vs_vertices.png'),('E','time_vs_edges.png')]:
 plt.figure(figsize=(9,5.5))
 for alg in sorted(set(r['algorithm'] for r in rows)):
  rr=sorted((r for r in rows if r['algorithm']==alg),key=lambda r:int(r[axis]))
  plt.plot([int(r[axis]) for r in rr],[float(r['average_ms']) for r in rr],marker='o',label=alg)
 plt.xlabel('Number of '+('vertices' if axis=='V' else 'edges'));plt.ylabel('Average time (ms), 5 runs');plt.title('Algorithm execution time vs '+('vertices' if axis=='V' else 'edges'));plt.legend(fontsize=8);plt.grid(alpha=.3);plt.tight_layout();plt.savefig('results/plots/'+file,dpi=170);plt.close()
