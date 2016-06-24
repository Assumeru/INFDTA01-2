package hro.infdta012;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import hro.infdta012.prediction.DoubleExponentialSmoothing;
import hro.infdta012.prediction.Graphs;

public class Exercise3 {
	private static final int WEEKS = 12;
	private static final int PREDICTION = 143 + 9;

	public static void main(String[] args) throws FileNotFoundException {
		List<Double> values = parseFile(args[0]);
		System.out.println(values.size());
		JFrame window = new JFrame();
		window.setTitle("Part 3");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(getGraphs(values));
		window.setSize(400, 300);
		window.setVisible(true);
	}

	private static List<Double> parseFile(String file) throws FileNotFoundException {
		List<Double> values = new ArrayList<>();
		try(Scanner scanner = new Scanner(new File(file))) {
			while(scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(",");
				if("1".equals(line[0]) && "1".equals(line[1])) {
					values.add(Double.parseDouble(line[3]));
				}
			}
		}
		return values;
	}

	private static Graphs getGraphs(List<Double> values) {
		DoubleExponentialSmoothing des = new DoubleExponentialSmoothing(values);
		double[] factors = des.getBestFactors();
		double[] forecastDES = des.getPredictions(factors[0], factors[1], PREDICTION);
		String desStats = "    \u03B1: " + factors[0] + " \u03B2: " + factors[1] + " Error measure: " + des.getErrorMeasure();
		return new Graphs(values, new double[] {}, "", forecastDES, desStats);
	}

}
