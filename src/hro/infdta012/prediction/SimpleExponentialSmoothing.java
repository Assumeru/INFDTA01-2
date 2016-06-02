package hro.infdta012.prediction;

import java.util.List;

public class SimpleExponentialSmoothing {
	private List<Double> values;
	private double[] oneStepForecast;
	private double smoothingFactor;

	public SimpleExponentialSmoothing(List<Double> values, double smoothingFactor) {
		this.values = values;
		this.smoothingFactor = smoothingFactor;
		calculateSmoothedValues();
	}

	private void calculateSmoothedValues() {
		oneStepForecast = new double[values.size() + 1];
		oneStepForecast[0] = getAvg(values);
		for(int i = 0; i < values.size(); i++) {
			oneStepForecast[i + 1] = smoothingFactor * values.get(i) + (1 - smoothingFactor) * oneStepForecast[i];
		}
	}

	private double getAvg(List<Double> values) {
		double sum = 0;
		//TODO 12
		for(int i = 0; i < 12; i++) {
			sum += values.get(i);
		}
		return sum / 12;
	}

	public double getSumOfSquaredErrors() {
		double sum = 0;
		for(int i = 0; i < values.size(); i++) {
			double d = values.get(i) - oneStepForecast[i];
			sum += d * d;
		}
		return sum;
	}
}
