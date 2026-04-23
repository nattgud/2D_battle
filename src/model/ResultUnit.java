package model;

public class ResultUnit {
	public Unit unit;
	public Unit target;
	public String type;
	public double score;
	public ResultUnit(Unit unit, Unit target, String type, double score) {
		this.unit = unit;
		this.target = target;
		this.type = type;
		this.score = score;
	}
}
