package hro.infdta012.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GeneticAlgorithm<T> {
	private Comparator<Individual<T>> comparator = (l, r) -> getFitness(r).compareTo(getFitness(l));
	private Map<T, Integer> fitness = new HashMap<>();
	private int bits;
	private double crossoverRate;
	private double mutationRate;
	private boolean elitism;
	private List<Individual<T>> population;
	private Function<T, Integer> fitnessAlgorithm;

	public GeneticAlgorithm(int bits, double crossoverRate, double mutationRate, boolean elitism, List<Individual<T>> population, Function<T, Integer> fitnessAlgorithm) {
		this.bits = bits;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.elitism = elitism;
		this.population = population;
		this.fitnessAlgorithm = fitnessAlgorithm;
	}

	public List<Individual<T>> run(int numIterations) {
		for(int i = 0; i < numIterations; i++) {
			population.sort(comparator);
			createNewPopulation();
		}
		population.sort(comparator);
		return population;
	}

	private void createNewPopulation() {
		int iterations = population.size();
		List<Individual<T>> newPopulation = new ArrayList<>(population.size());
		if(elitism) {
			iterations--;
			newPopulation.add(population.get(0));
		}
		int fitnessSum = 0;
		for(Individual<T> individual : population) {
			fitnessSum += getFitness(individual);
		}
		while(iterations > 0) {
			Collection<Individual<T>> children = getChildren(fitnessSum);
			for(Individual<T> child : children) {
				if(newPopulation.size() < population.size()) {
					newPopulation.add(child.mutate(mutationRate, bits));
				} else {
					iterations = 0;
					break;
				}
			}
		}
		population = newPopulation;
	}

	private Collection<Individual<T>> getChildren(int fitnessSum) {
		Individual<T> p1 = selectParent(fitnessSum, null);
		Individual<T> p2 = selectParent(fitnessSum - getFitness(p1), p1);
		if(Math.random() < crossoverRate) {
			return Arrays.asList(p1.breed(p2), p2.breed(p1));
		}
		return Arrays.asList(p1, p2);
	}

	private Individual<T> selectParent(double fitnessSum, Individual<T> skip) {
		double sum = 0;
		double random = fitnessSum * Math.random();
		Individual<T> last = null;
		for(Individual<T> individual : population) {
			if(individual == skip) {
				continue;
			}
			last = individual;
			sum += getFitness(individual) / fitnessSum;
			if(random < sum) {
				return individual;
			}
		}
		return last;
	}

	public Integer getFitness(Individual<T> individual) {
		Integer out = fitness.get(individual.getValue());
		if(out == null) {
			out = fitnessAlgorithm.apply(individual.getValue());
			fitness.put(individual.getValue(), out);
		}
		return out;
	}
}
