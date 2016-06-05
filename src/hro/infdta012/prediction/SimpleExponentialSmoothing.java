package hro.infdta012.prediction;

import java.util.ArrayList;
import java.util.List;

import hro.infdta012.genetic.GeneticAlgorithm;
import hro.infdta012.genetic.Individual;
import hro.infdta012.genetic.IntegerIndividual;

public class SimpleExponentialSmoothing {
	private static final int BITS = 20;
	private static final double MAX_VALUE = Math.pow(2, BITS) - 1;
	private static final double CROSSOVER_RATE = 0.5;
	private static final double MUTATION_RATE = 0.05;
	private static final int POPULATION_SIZE = 20;
	private static final int ITERATIONS = 1000;
	private List<Double> values;
	private double[] oneStepForecast;

	public SimpleExponentialSmoothing(List<Double> values, int max) {
		this.values = values;
		oneStepForecast = new double[values.size() + 1];
		oneStepForecast[0] = getAvg(max);
	}

	private double getAvg(int max) {
		double sum = 0;
		for(int i = 0; i < max; i++) {
			sum += values.get(i);
		}
		return sum / max;
	}

	private void calculateSmoothedValues(double smoothingFactor) {
		double inverse = 1 - smoothingFactor;
		for(int i = 0; i < values.size(); i++) {
			oneStepForecast[i + 1] = smoothingFactor * values.get(i) + inverse * oneStepForecast[i];
		}
	}

	private double getSumOfSquaredErrors() {
		double sum = 0;
		for(int i = 0; i < values.size(); i++) {
			double d = values.get(i) - oneStepForecast[i];
			sum += d * d;
		}
		return sum;
	}

	private double getFitness(int input) {
		double factor = input / MAX_VALUE;
		calculateSmoothedValues(factor);
		return -getSumOfSquaredErrors();
	}

	public double getBestSmoothingFactor() {
		GeneticAlgorithm<Integer> algorithm = new GeneticAlgorithm<>(BITS, CROSSOVER_RATE, MUTATION_RATE, true, initPopulation(), this::getFitness);
		List<Individual<Integer>> out = algorithm.run(ITERATIONS);
		return out.get(0).getValue() / MAX_VALUE;
	}

	private List<Individual<Integer>> initPopulation() {
		List<Individual<Integer>> population = new ArrayList<>(POPULATION_SIZE);
		for(int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new IntegerIndividual(BITS));
		}
		return population;
	}

	public double[] getPredictions(double smoothingFactor, int maxT) {
		calculateSmoothedValues(smoothingFactor);
		double[] out = new double[maxT];
		System.arraycopy(oneStepForecast, 1, out, 0, Math.min(maxT, oneStepForecast.length - 1));
		for(int i = values.size(); i < maxT; i++) {
			out[i] = oneStepForecast[oneStepForecast.length - 1];
		}
		return out;
	}
}
