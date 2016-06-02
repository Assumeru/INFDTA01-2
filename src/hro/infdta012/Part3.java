package hro.infdta012;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hro.infdta012.prediction.SimpleExponentialSmoothing;

public class Part3 {
	public static void main(String[] args) throws FileNotFoundException {
		double smoothingFactor = Double.parseDouble(args[0]);
		List<Double> values = parseFile(args[1]);
		SimpleExponentialSmoothing ses = new SimpleExponentialSmoothing(values, smoothingFactor);
		System.out.println(ses.getSumOfSquaredErrors());
	}

	private static List<Double> parseFile(String file) throws FileNotFoundException {
		List<Double> values = new ArrayList<>();
		try(Scanner scanner = new Scanner(new File(file))) {
			while(scanner.hasNextDouble()) {
				values.add(scanner.nextDouble());
			}
		}
		return values;
	}
}
