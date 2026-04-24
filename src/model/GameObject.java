package model;

import Util.Settings;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
	public boolean solid = true;
	protected Position pos;
	protected Texture texture;
	public BufferedImage texture() {
		return this.texture.image();
	}
	public boolean isHovered(Position pos) {
		if(this.pos == null || pos == null) {
			return false;
		}
		return pos.x >= this.pos.x*Settings.scale && pos.x < (this.pos.x*Settings.scale)+Settings.scale && pos.y >= this.pos.y*Settings.scale && pos.y < (this.pos.y*Settings.scale)+Settings.scale;
	}
	public Position pos() {
		return new Position(this.pos.x, this.pos.y);
	}
	public void moveTo(Position newPos) {
		this.pos.x = newPos.x;
		this.pos.y = newPos.y;
	}
}
