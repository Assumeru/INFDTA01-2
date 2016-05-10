package hro.infdta012.cluster;

import java.util.List;

import hro.infdta012.similarity.DistanceCalculator;

public class KMeans implements ClusteringAlgorithm {
	private DistanceCalculator calculator;

	public KMeans(DistanceCalculator calculator) {
		this.calculator = calculator;
	}

	@Override
	public void initClusters(Cluster[] clusters, List<Point> points) {
		for(int i = 0; i < clusters.length; i++) {
			clusters[i] = new Cluster(getRandomPoint(points));
		}
	}

	private Point getRandomPoint(List<Point> points) {
		return points.get((int) (Math.random() * points.size()));
	}

	@Override
	public void assignPoints(List<Point> points, Cluster[] clusters) {
		for(Point p : points) {
			double d = Double.POSITIVE_INFINITY;
			Cluster c = clusters[0];
			int i = 0;
			do {
				double delta = clusters[i].compare(calculator, p);
				if(delta < d) {
					d = delta;
					c = clusters[i];
				}
				i++;
			} while(i < clusters.length);
			c.getPoints().add(p);
		}
	}

	@Override
	public void setCenters(Cluster[] clusters) {
		for(Cluster cluster : clusters) {
			cluster.setPosition(new Point(cluster.getMean()));
		}
	}
}
