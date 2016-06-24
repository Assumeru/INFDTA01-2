package hro.infdta012;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import hro.infdta012.genetic.ByteIndividual;
import hro.infdta012.genetic.GeneticAlgorithm;
import hro.infdta012.genetic.Individual;

public class Exercise2 {
	private static final int BITS = 7;
	private static final int SMALL_MOUTH = 1;
	private static final int IMPROVED_SENSES = 1 << 1;
	private static final int AGILITY = 1 << 2;
	private static final int LARGER_BRAIN = 1 << 3;
	private static final int HARD_SKIN = 1 << 4;
	private static final int LEG_MUSCLES = 1 << 5;
	private static final int LONG_NECK = 1 << 6;

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
		Function<Byte, Double> fitnessAlgorithm = in -> {
			double a = 10;
			double b = 100;
			double c = 10;
			double d = 50;
			double e = 2;
			double f = 5;
			int val = in & 0xFF;
			if((val & SMALL_MOUTH) == SMALL_MOUTH) {
				b *= 0.1;
				c *= 5;
			}
			if((val & IMPROVED_SENSES) == IMPROVED_SENSES) {
				a *= 2.5;
				b *= 2.5;
				c *= 0.5;
			}
			if((val & AGILITY) == AGILITY) {
				e *= 2.5;
				f *= 0.1;
			}
			if((val & LARGER_BRAIN) == LARGER_BRAIN) {
				c *= 0.2;
				e *= 1.75;
				f *= 0.35;
			}
			if((val & HARD_SKIN) == HARD_SKIN) {
				a *= 1.75;
				e *= 3;
				f *= 0.5;
			}
			if((val & LEG_MUSCLES) == LEG_MUSCLES) {
				a *= 0.3;
				e *= 3;
			}
			if((val & LONG_NECK) == LONG_NECK) {
				b *= 4;
				d *= 3.5;
			}
			return b / (a + c + d) - e - f / d;
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
		System.out.println(Integer.toBinaryString(population.get(0).getValue() & 0xFF));
	}

	private static List<Individual<Byte>> initPopulation(int size) {
		List<Individual<Byte>> population = new ArrayList<>(size);
		for(int i = 0; i < size; i++) {
			population.add(new ByteIndividual(BITS));
		}
		return population;
	}
}
