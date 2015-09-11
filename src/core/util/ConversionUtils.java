package core.util;

import java.awt.Polygon;
import java.awt.Shape;

public class ConversionUtils {
	public static int[] mapToInt(String[] values) {
		int[] result = new int[values.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = Integer.parseInt(values[i], 10);
		}
		
		return result;
	}
	
	public static int[] mapToInt(String value, String delimiter) {
		return mapToInt(value.split(delimiter));
	}
	
	public static Shape pointsToShape(int[] offset, int[] path) {
		int npoints = path.length / 2;
		int[] xpoints = new int[npoints];
		int[] ypoints = new int[npoints];
		
		for (int i = 0; i < npoints; i++) {
			xpoints[i] = path[i * 2] - offset[0];
			ypoints[i] = path[i * 2 + 1] - offset[1];
		}
		
		return new Polygon(xpoints, ypoints, npoints);
	}
}
