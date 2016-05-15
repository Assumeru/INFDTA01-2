package hro.infdta012.cluster;

import java.util.HashSet;
import java.util.Set;

import hro.infdta012.similarity.DistanceCalculator;
import hro.infdta012.similarity.DistanceComparable;
import hro.infdta012.similarity.SquaredEuclidianDistance;

public class Cluster implements DistanceComparable<Point> {
	private static final DistanceCalculator SQUARED_DISTANCE = new SquaredEuclidianDistance();
	private Set<Point> points;
	private Point position;

	public Cluster(Point position) {
		points = new HashSet<>();
		this.position = position;
	}

	public Set<Point> getPoints() {
		return points;
	}

	@Override
	public double compare(DistanceCalculator calculator, Point other) {
		return position.compare(calculator, other);
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public double[] getMean() {
		double[] sums = new double[position.getProperties().length];
		for(Point p : points) {
			for(int i = 0; i < p.getProperties().length; i++) {
				sums[i] += p.getProperties()[i];
			}
		}
		for(int i = 0; i < sums.length; i++) {
			sums[i] /= points.size();
		}
		return sums;
	}

	public double getSSE() {
		double sum = 0;
		for(Point p : points) {
			sum += compare(SQUARED_DISTANCE, p);
		}
		return sum;
	}

	public Point getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "Cluster(" + points.size() + ", " + getSSE() + ") {" + points.toString() + "}";
	}
}
