package hro.infdta012;

import hro.infdta012.similarity.DistanceCalculator;
import hro.infdta012.similarity.DistanceComparable;

public class Point implements DistanceComparable<Point> {
	private double[] properties;

	public Point(double[] properties) {
		this.properties = properties;
	}

	@Override
	public double compare(DistanceCalculator calculator, Point other) {
		return calculator.getDistance(properties, other.properties);
	}
}
