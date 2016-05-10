package hro.infdta012.cluster;

import java.util.List;

public class ClusterCreator {
	private List<Point> points;
	private ClusteringAlgorithm algorithm;

	public ClusterCreator(List<Point> points, ClusteringAlgorithm algorithm) {
		this.points = points;
		this.algorithm = algorithm;
	}

	public Cluster[] createClusters(int numClusters, int numIterations) {
		Cluster[] clusters = new Cluster[numClusters];
		algorithm.initClusters(clusters, points);
		while(numIterations > 0) {
			long t = System.nanoTime();
			for(Cluster c : clusters) {
				c.getPoints().clear();
			}
			algorithm.assignPoints(points, clusters);
			algorithm.setCenters(clusters);
			numIterations--;
			System.out.println("Finished in: " + ((System.nanoTime() - t) / 1000000.0));
		}
		return clusters;
	}
}
