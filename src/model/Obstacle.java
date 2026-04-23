package model;

public class Obstacle extends GameObject {
	public Obstacle(Position pos, Texture texture) {
		this.pos = pos;
		this.texture = texture;
		this.solid = true;
	}
}