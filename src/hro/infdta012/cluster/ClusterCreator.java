package hro.infdta012.cluster;

import java.util.List;

public class ClusterCreator {
	private List<Point> points;
	private ClusteringAlgorithm algorithm;

	public ClusterCreator(List<Point> points, ClusteringAlgorithm algorithm) {
		this.points = points;
		this.algorithm = algorithm;
	}

	private Cluster[] createClusters(int numClusters) {
		Cluster[] clusters = new Cluster[numClusters];
		algorithm.initClusters(clusters, points);
		int previous;
		int hash = 0;
		do {
			previous = hash;
			for(Cluster c : clusters) {
				c.getPoints().clear();
			}
			algorithm.assignPoints(points, clusters);
			algorithm.setCenters(clusters);
		} while((hash = getHash(clusters)) != previous);
		return clusters;
	}

	private int getHash(Cluster[] clusters) {
		int hash = 0;
		for(Cluster c : clusters) {
			hash *= 31;
			hash += c.getPoints().hashCode();
		}
		return hash;
	}

	public Cluster[] createClusters(int numClusters, int numIterations) {
		double best = Double.POSITIVE_INFINITY;
		Cluster[] out = null;
		while(numIterations > 0) {
			Cluster[] clusters = createClusters(numClusters);
			double d = getSSE(clusters);
			if(d < best) {
				best = d;
				out = clusters;
			}
			numIterations--;
		}
		return out;
	}

	private double getSSE(Cluster[] clusters) {
		double sum = 0;
		for(Cluster c : clusters) {
			sum += c.getSSE();
		}
		return sum;
	}
}
