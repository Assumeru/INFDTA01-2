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
			System.out.println(c);
		}
	}
}
