package model;

import Util.Settings;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Particle extends GameObject {
	private Position destination;
	private int speed;
	private boolean done;
	private String type = "none";
	private int lifetime;
	private int fullLifetime;
	private Map<String, Texture> shotTypes = Map.of(
			"arrow", Texture.ARROW_RIGHT,
			"magic", Texture.ARROW_MAGIC,
			"fire", Texture.ARROW_FIRE,
			"water", Texture.ARROW_WATER
	);
	public Particle(Texture texture, Position pos, int lifetime) {
		this.pos = pos;
		this.texture = texture;
		this.lifetime = lifetime;
		this.fullLifetime = this.lifetime;
	}
	public Particle(Position pos, Position destination) {
		this.pos = pos;
		this.destination = destination;
	}
	public Particle(String type, Position pos, Position destination, int speed) {
		this.type = type;
		if(shotTypes.containsKey(type)) this.texture = shotTypes.get(type);
		this.pos = pos;
		this.destination = destination;
		this.speed = speed;
	}
	public String type() {
		return this.type;
	}
	public int lifetime() {
		return this.lifetime;
	}
	public float lifetimePercent() {
		return (this.fullLifetime == 0)
				? 0f
				: (float) this.lifetime / this.fullLifetime;
	}
	public void live() {
		this.lifetime--;
		if(this.lifetime <= 0) {
			this.done = true;
		}
	}
	public void move() {
		double dx = this.destination.x - this.pos.x;
		double dy = this.destination.y - this.pos.y;
		double distance = Math.sqrt(dx * dx + dy * dy);
		if (distance <= this.speed) {
			this.pos.x = this.destination.x;
			this.pos.y = this.destination.y;
			this.done = true;
		} else {
			double angle = Math.atan2(dy, dx);
			if (type.equals("arrow")) {
				if (angle >= -Math.PI/4 && angle < Math.PI/4) {
					this.texture = Texture.ARROW_RIGHT;
				} else if (angle >= Math.PI/4 && angle < 3*Math.PI/4) {
					this.texture = Texture.ARROW_DOWN;
				} else if (angle < -Math.PI/4 && angle >= -3*Math.PI/4) {
					this.texture = Texture.ARROW_UP;
				} else {
					this.texture = Texture.ARROW_LEFT;
				}
			}
			this.pos.x += Math.cos(angle) * this.speed;
			this.pos.y += Math.sin(angle) * this.speed;
		}
	}
	public boolean done() {
		return this.done;
	}
}
