package Util;

public class Random {
	public static double randomDouble(double min, double max) {
		return (min + Math.random() * (max - min));
	}
	public static float randomFloat(float min, float max) {
		return (float) (min + Math.random() * (max - min));
	}
	public static int randomInt(int min, int max) {
		return (int) Math.floor(min + Math.random() * (max - min + 1));
	}
}
