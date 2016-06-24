package hro.infdta012;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hro.infdta012.cluster.Cluster;
import hro.infdta012.cluster.ClusterCreator;
import hro.infdta012.cluster.KMeans;
import hro.infdta012.cluster.Point;
import hro.infdta012.similarity.EuclidianDistance;

public class Exercise1 {
	public static void main(String[] args) throws FileNotFoundException {
		int numClusters = Integer.parseInt(args[0]);
		int numIterations = Integer.parseInt(args[1]);
		List<Point> points = parsePoints(new File(args[2]));
		ClusterCreator creator = new ClusterCreator(points, new KMeans(new EuclidianDistance()));
		Cluster[] clusters = creator.createClusters(numClusters, numIterations);
		System.out.println("SSE: " + creator.getSSE(clusters));
		System.out.println();
		for(Cluster c : clusters) {
			postProcess(c);
		}
	}

	private static List<Point> parsePoints(File file) throws FileNotFoundException {
		List<Point> points = new ArrayList<>();
		try(Scanner sc = new Scanner(file)) {
			while(sc.hasNextInt()) {
				double[] p = new double[] {sc.nextInt(), sc.nextInt()};
				points.add(new Point(p));
			}
		}
		return points;
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
		System.out.println("Clients: " + c.getPoints().size());
		for(int i = 0; i < count.length; i++) {
			System.out.println((i + 1) + " was bought " + count[i] + " times");
		}
		System.out.println();
	}
}
