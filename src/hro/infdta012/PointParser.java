package hro.infdta012;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PointParser {
	private List<Point> points;
	private File file;

	public PointParser(String file) {
		this.file = new File(file);
	}

	public List<Point> parsePoints() throws FileNotFoundException {
		points = new ArrayList<>();
		try(Scanner sc = new Scanner(file)) {
			while(sc.hasNextLine()) {
				parseLine(sc.nextLine());
			}
		}
		return points;
	}

	private void parseLine(String line) {
		String[] properties = line.split(",");
		if(properties.length > 0) {
			double[] values = new double[properties.length];
			for(int i = 0; i < values.length; i++) {
				values[i] = Double.parseDouble(properties[i]);
			}
			points.add(new Point(values));
		}
	}
}
