package hro.infdta012;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import hro.infdta012.prediction.SESFrame;
import hro.infdta012.prediction.SimpleExponentialSmoothing;

public class Part3 {
	private static final int MONTHS = 12;
	private static final int PREDICTION = 49;

	public static void main(String[] args) throws FileNotFoundException {
		List<Double> values = parseFile(args[0]);
		JFrame window = new JFrame();
		window.setTitle("Part 3");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(getSES(values));
		window.setSize(400, 300);
		window.setVisible(true);
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

	private static SESFrame getSES(List<Double> values) {
		SimpleExponentialSmoothing ses = new SimpleExponentialSmoothing(values, MONTHS);
		double factor = ses.getBestSmoothingFactor();
		double[] forecast = ses.getPredictions(factor, PREDICTION);
		return new SESFrame(values, forecast, factor);
	}
}
