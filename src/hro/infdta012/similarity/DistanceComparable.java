package hro.infdta012.similarity;

public interface DistanceComparable<T> {
	double compare(DistanceCalculator calculator, T other);
}
