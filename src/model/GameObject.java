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
	public boolean canReach(Position target) {
		return false;
	}

	public void drawText(Graphics2D canvas, String text, int x, int y, Color color, int size, boolean bold) {
		canvas.setFont(new Font("Arial", bold?Font.BOLD:Font.PLAIN, size));
		canvas.setColor(Color.BLACK);
		if(bold) {
			canvas.drawString(text, x + 1, y + 1);
			canvas.drawString(text, x - 1, y - 1);
			canvas.drawString(text, x + 1, y - 1);
			canvas.drawString(text, x - 1, y + 1);
			canvas.drawString(text, x + 1, y);
			canvas.drawString(text, x - 1, y);
			canvas.drawString(text, x, y - 1);
			canvas.drawString(text, x, y + 1);
		}
		canvas.setColor(color);
		canvas.drawString(text, x, y);
	}
	public void drawText(Graphics2D canvas, String text, int x, int y, Color color) {
		canvas.setFont(new Font("Arial", Font.BOLD, 12));
		canvas.setColor(Color.BLACK);
		canvas.drawString(text, x + 1, y + 1);
		canvas.drawString(text, x - 1, y - 1);
		canvas.drawString(text, x + 1, y - 1);
		canvas.drawString(text, x - 1, y + 1);
		canvas.drawString(text, x + 1, y);
		canvas.drawString(text, x - 1, y);
		canvas.drawString(text, x, y - 1);
		canvas.drawString(text, x, y + 1);
		canvas.setColor(color);
		canvas.drawString(text, x, y);
	}
}
