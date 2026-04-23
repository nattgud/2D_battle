package model;

import java.awt.*;

public class Button {
	private Position pos;
	private Position size;
	private String text;
	private int textSize;
	private boolean disabled;
	Button(Position pos, String text, int size, boolean disabled) {
		this.pos = pos;
		this.text = text;
		this.textSize = size;
		this.disabled = disabled;
	}
	Button(Position pos, String text, int size) {
		this.pos = pos;
		this.text = text;
		this.textSize = size;
	}
	Button(Position pos, String text) {
		this.pos = pos;
		this.text = text;
	}
	public void draw(Graphics2D g, GameRenderer gr, Position mousePos) {
		g.setFont(new Font("Arial", Font.BOLD, this.textSize));
		FontMetrics fm = g.getFontMetrics();
		int width = fm.stringWidth(this.text)+20;
		int height = this.textSize+20;
		this.size = new Position(width, height);
		boolean hovered = this.mouseOver(mousePos);
		Color col = this.disabled?(new Color(150, 150, 150)):(hovered?Color.GREEN:Color.WHITE);
		g.setColor(col);
		g.fillRect(pos.x, pos.y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(pos.x, pos.y, width, height);
		gr.drawText(g, new Position(pos.x+(width/2), pos.y+(height/2)), text, this.textSize);
	}
	public boolean mouseOver(Position mousePos) {
		boolean hovered = (
				mousePos.x >= pos.x &&
				mousePos.x <= pos.x+this.size.x &&
				mousePos.y >= pos.y &&
				mousePos.y <= pos.y+this.size.y
		);
		return hovered;
	}
}
