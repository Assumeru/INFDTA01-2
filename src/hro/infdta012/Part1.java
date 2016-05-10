package hro.infdta012;

import java.util.List;

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
		EuclidianDistance c = new EuclidianDistance();
		for(int i = 1; i < points.size(); i++) {
			System.out.println(points.get(0).compare(c, points.get(i)));
		}
	}
}
