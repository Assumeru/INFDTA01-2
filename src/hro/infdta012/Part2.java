package hro.infdta012;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import hro.infdta012.genetic.ByteIndividual;
import hro.infdta012.genetic.GeneticAlgorithm;
import hro.infdta012.genetic.Individual;

public class Part2 {
	private static final int BITS = 5;

	public static void main(String[] args) {
		double crossoverRate = Double.parseDouble(args[0]);
		double mutationRate = Double.parseDouble(args[1]);
		boolean elitism = Boolean.parseBoolean(args[2]);
		int populationSize = Integer.parseInt(args[3]);
		int numIterations = Integer.parseInt(args[4]);
		if(populationSize < 2) {
			System.err.println("Not enough possible parents");
			return;
		}
		List<Individual<Byte>> population = initPopulation(populationSize);
		Function<Byte, Integer> fitnessAlgorithm = b -> {
			int x = b & 0xFF;
			return x * (7 - x);
		};

		GeneticAlgorithm<Byte> algorithm = new GeneticAlgorithm<>(BITS, crossoverRate, mutationRate, elitism, population, fitnessAlgorithm);
		printResult(algorithm.run(numIterations), algorithm);
	}

	private static void printResult(List<Individual<Byte>> population, GeneticAlgorithm<Byte> algorithm) {
		System.out.println(population);
		double avgFitness = 0;
		for(Individual<Byte> individual : population) {
			avgFitness += algorithm.getFitness(individual);
		}
		avgFitness /= population.size();
		System.out.println("Average fitness: " + avgFitness);
		System.out.println("Best fitness: " + algorithm.getFitness(population.get(0)));
		System.out.println("Best individual: " + population.get(0).getValue());
	}

	private static List<Individual<Byte>> initPopulation(int size) {
		List<Individual<Byte>> population = new ArrayList<>(size);
		for(int i = 0; i < size; i++) {
			population.add(new ByteIndividual(BITS));
		}
		return population;
	}
}
