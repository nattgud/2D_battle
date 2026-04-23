package model;

import Util.Settings;

import java.util.Objects;

public class Position {
	public int x;
	public int y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Position realPos() {
		return new Position(this.x * Settings.scale, this.y * Settings.scale);
	}
	public static Position toRealPos(int x, int y) {
		return new Position(x * Settings.scale, y * Settings.scale);
	}
	public static Position toRealPos(Position pos) {
		return new Position(pos.x * Settings.scale, pos.y * Settings.scale);
	}
	public static Position gamePos(int x, int y) {
		return new Position((int) Math.floor(x / Settings.scale), (int) Math.floor(y / Settings.scale));
	}
	public static Position gamePos(Position pos) {
		return new Position((int) Math.floor(pos.x / Settings.scale), (int) Math.floor(pos.y / Settings.scale));
	}
	public String toString() {
		return "x:"+this.x+" y:"+this.y;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Position p = (Position) o;
		return x == p.x && y == p.y;
	}
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
