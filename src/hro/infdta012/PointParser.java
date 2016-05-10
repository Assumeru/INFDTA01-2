package hro.infdta012;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hro.infdta012.cluster.Point;

public class PointParser {
	private List<Point> points;
	private File file;

	public PointParser(String file) {
		this.file = new File(file);
	}

	public List<Point> parsePoints() throws FileNotFoundException {
		points = new ArrayList<>();
		List<String[]> lines = new ArrayList<>();
		try(Scanner sc = new Scanner(file)) {
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if(line != null && !line.isEmpty()) {
					lines.add(line.split(","));
				}
			}
		}
		parseLines(lines);
		return points;
	}

	private void parseLines(List<String[]> lines) {
		for(int i = 0; i < lines.get(0).length; i++) {
			double[] values = new double[lines.size()];
			for(int j = 0; j < values.length; j++) {
				values[j] = Double.parseDouble(lines.get(j)[i]);
			}
			points.add(new Point(values));
		}
	}
}
