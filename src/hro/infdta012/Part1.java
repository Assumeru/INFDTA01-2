package hro.infdta012;

import java.util.List;

import hro.infdta012.cluster.Cluster;
import hro.infdta012.cluster.ClusterCreator;
import hro.infdta012.cluster.KMeans;
import hro.infdta012.cluster.Point;
import hro.infdta012.similarity.EuclidianDistance;

public class Part1 {
	public static void main(String[] args) throws Exception {
		if(args.length < 3) {
			System.out.println("Usage: <numClusters> <numIterations> <data>");
			System.exit(1);
		}
		int numClusters = Integer.parseInt(args[0]);
		int numIterations = Integer.parseInt(args[1]);
		List<Point> points = new PointParser(args[2]).parsePoints();
		ClusterCreator creator = new ClusterCreator(points, new KMeans(new EuclidianDistance()));
		Cluster[] clusters = creator.createClusters(numClusters, numIterations);
		for(Cluster c : clusters) {
			postProcess(c);
		}
	}

	private static void postProcess(Cluster c) {
		int[] count = new int[c.getPosition().getProperties().length];
		for(Point p : c.getPoints()) {
			for(int i = 0; i < count.length; i++) {
				if(p.getProperties()[i] > 0) {
					count[i]++;
				}
			}
		}
		System.out.println("SSE: " + c.getSSE());
		System.out.println("Clients: " + c.getPoints().size());
		for(int i = 0; i < count.length; i++) {
			if(count[i] > 3) {
				System.out.println(i + " was bought " + count[i] + " times");
			}
		}
		System.out.println();
	}
}
