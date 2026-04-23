package model;

import Util.Pathfinder;
import Util.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameRenderer {
	private Map<String, Texture> shotTypes = Map.of(
			"arrow", Texture.ARROW_RIGHT,
			"magic", Texture.ARROW_MAGIC,
			"fire", Texture.ARROW_FIRE,
			"water", Texture.ARROW_WATER
	);
	private Map<String, Color> raceColors = Map.of(
			"mortal", new Color(180, 180, 180),
			"beast", new Color(255, 120, 0),
			"magic", new Color(0, 200, 255),
			"dragon", new Color(255, 100, 100),
			"divine", new Color(255, 255, 100),
			"unholy", new Color(0, 255, 0)
	);
	private HashMap<String, Button> buttons = new HashMap<>();
	public void setAlpha(Graphics2D g, float value) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
	}
	public void drawGameObject(Graphics2D canvas, GameObject obj) {
		drawImage(canvas, obj.texture(), (int) obj.pos().x * Settings.scale, (int) obj.pos().y * Settings.scale, Settings.scale, Settings.scale);
	}
	public void drawGameObjectList(Graphics2D g, List<? extends GameObject> list) {
		for (GameObject go : list) this.drawGameObject(g, go);
	}
	public void drawParticle(Graphics2D canvas, Particle p) {
		float alpha = p.lifetimePercent();
		if(alpha <= 0) alpha = 0;
		if(!shotTypes.containsKey(p.type())) canvas.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		drawImage(canvas, p.texture(), (int) p.pos().x, (int) p.pos().y, Settings.scale, Settings.scale);
		canvas.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	public void drawParticleList(Graphics2D g, List<? extends Particle> list) {
		for (Particle particle : list) this.drawParticle(g, particle);
	}
	private void drawImage(Graphics2D g, BufferedImage img, int x, int y, int width, int height) {
		g.drawImage(img, x, y, width, height, null);
	}
	public void drawRectangle(Graphics2D g, Position pos, Position size, Color bg, Color outline) {
		if(bg != null) {
			g.setColor(bg);
			g.fillRect(pos.x, pos.y, size.x, size.y);
		}
		if(outline != null) {
			g.setColor(outline);
			g.drawRect(pos.x, pos.y, size.x, size.y);
		}
	}
	public void drawText(Graphics2D g, Position pos, String text, int size) {
		drawText(g, pos, text, size, Color.BLACK, false, false);
	}
	public void drawText(Graphics2D g, Position pos, String text, int size, Color col) {
		drawText(g, pos, text, size, col, false, false);
	}
	public Position drawText(Graphics2D g, Position pos, String text, int size, Color col, boolean outline, boolean bold) {
		g.setFont(new Font("Arial", (bold)?Font.BOLD:Font.PLAIN, size));
		FontMetrics fm = g.getFontMetrics();
		int x = pos.x - ((fm.stringWidth(text)/2));
		int y = pos.y + ((fm.getAscent() - fm.getDescent()) / 2);
		int width = fm.stringWidth(text)+20;
		int height = size+20;
		if(outline) {
			g.setColor(Color.BLACK);
			for(int xm = -1; xm <= 1; xm++) {
				for (int ym = -1; ym <= 1; ym++) {
					if (xm == 0 && ym == 0) continue;
					g.drawString(text, x + xm, y + ym);
					g.drawString(text, x + xm, y + ym);
					g.drawString(text, x + xm, y + ym);
					g.drawString(text, x + xm, y + ym);
					g.drawString(text, x + xm, y + ym);
					g.drawString(text, x + xm, y + ym);
					g.drawString(text, x + xm, y + ym);
					g.drawString(text, x + xm, y + ym);
				}
			}
		}
		g.setColor(col);
		g.drawString(text, x, y);
		return new Position(width, height);
	}
	public void drawTextWithBackground(Graphics2D g, Position pos, String text, int size) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		FontMetrics fm = g.getFontMetrics();
		int width = fm.stringWidth(text)+20;
		int height = size+20;
		g.setColor(Color.WHITE);
		g.fillRect(pos.x, pos.y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(pos.x, pos.y, width, height);
		this.drawText(g, new Position(pos.x+(width/2), pos.y+(height/2)), text, size);
	}
	public void drawTextWithBackground(Graphics2D g, Position pos, String text, int size, Color col) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		FontMetrics fm = g.getFontMetrics();
		int width = fm.stringWidth(text)+20;
		int height = size+20;
		g.setColor(Color.WHITE);
		if(col == Color.WHITE) {
			g.setColor(Color.BLACK);
		}
		g.fillRect(pos.x, pos.y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(pos.x, pos.y, width, height);
		this.drawText(g, new Position(pos.x+(width/2), pos.y+(height/2)), text, size, col);
	}
	public void drawTextWithBackground(Graphics2D g, Position pos, String text, int size, Color col, Color bg) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		FontMetrics fm = g.getFontMetrics();
		int width = fm.stringWidth(text)+20;
		int height = size+20;
		g.setColor(bg);
		g.fillRect(pos.x, pos.y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(pos.x, pos.y, width, height);
		this.drawText(g, new Position(pos.x+(width/2), pos.y+(height/2)), text, size, col);
	}
	public void drawOutlinedText(Graphics2D g, String text, int x, int y, int fontSize, Color color) {
		g.setFont(new Font("Arial", Font.BOLD, fontSize));
		g.setColor(Color.BLACK);
		for (int dx = -1; dx <= 1; dx++)
			for (int dy = -1; dy <= 1; dy++)
				if (dx != 0 || dy != 0) g.drawString(text, x + dx, y + dy);
		g.setColor(color);
		g.drawString(text, x, y);
	}
	public void addButton(String name, Position pos, String text, int size, boolean disabled) {
		this.buttons.put(name, new Button(pos, text, size, disabled));
	}
	public void addButton(String name, Position pos, String text, int size) {
		this.buttons.put(name, new Button(pos, text, size, false));
	}
	public void addButton(String name, Position pos, String text) {
		this.buttons.put(name, new Button(pos, text, 72, false));
	}
	public void drawButton(String buttonName, Graphics2D g, Position mousePos) {
		try {
			this.buttons.get(buttonName).draw(g, this, mousePos);
		} catch(Exception e) {
			System.out.println("Button "+buttonName+" doesn't exist");
		}
	}
	public boolean mouseOverButton(String buttonName, Position mousePos) {
		try {
			return this.buttons.get(buttonName).mouseOver(mousePos);
		} catch(Exception e) {
			System.out.println("Button "+buttonName+" doesn't exist");
		}
		return false;
	}

	public void drawUI(Graphics2D g, Unit unit, boolean hovered, int infoType) {
		// no UI if dead
		if(!unit.alive()) return;
		// ranged+flying icons
		if(hovered) {
			if (unit.isRanged())
				drawImage(
						g,
						Texture.ICON_RANGED.image(),
						(int) ((unit.pos.x * Settings.scale) + (Settings.scale * 0.7)),
						(int) ((unit.pos.y * Settings.scale) + (Settings.scale * 0.1)),
						(int) (Settings.scale * 0.3),
						(int) (Settings.scale * 0.3));
			if (unit.isFlying())
				drawImage(
						g,
						Texture.ICON_FLIGHT.image(),
						(int) ((unit.pos.x * Settings.scale) + (Settings.scale * 0.6)),
						(int) ((unit.pos.y * Settings.scale) + (Settings.scale * 0.4)),
						(int) (Settings.scale * 0.4), (int) (Settings.scale * 0.4)
				);
		}
		// HP-bar
		drawRectangle(
				g,
				new Position(unit.pos.x * Settings.scale, unit.pos.y * Settings.scale),
				new Position(Settings.scale, 2),
				Color.RED,
				null);
		drawRectangle(
				g,
				new Position(unit.pos.x * Settings.scale, unit.pos.y * Settings.scale),
				new Position((int) (((double) unit.hp()/(double) unit.fullHp()) * Settings.scale), 2),
				Color.GREEN,
				null);
		// Unit name
		drawText(
				g,
				new Position((unit.pos.x * Settings.scale)+(Settings.scale/2), (unit.pos.y * Settings.scale)+Settings.scale-5),
				unit.name(),
				12,
				Color.WHITE,
				true,
				false);

		//		DELETE
		g.setFont(new Font("Arial", Font.BOLD, 12));		// DELETE
		FontMetrics fm = g.getFontMetrics();						// DELETE


		if(hovered) {
			// --- Unit hover-tooltip
			int xmid = (Settings.width*Settings.scale)/2;
			if(infoType == 2) {
				if (unit.pos.x > Settings.width / 2) {
					xmid = ((unit.pos.x*Settings.scale)-200);
				} else if (unit.pos.x <= Settings.width / 2) {
					xmid = ((unit.pos.x*Settings.scale)+Settings.scale+200);
				}
			}
			// background
			drawRectangle(
					g,
					new Position(xmid-150, 50),
					new Position(300, 100+(unit.numberOfStrengths()*20)+20),
					Color.WHITE,
					Color.BLACK);
			// unit name
			drawText(
					g,
					new Position(xmid, 75),
					unit.name(),
					32,
					Color.BLACK,
					false,
					true);
			// health
			drawText(
					g,
					new Position(xmid-100, 110),
					"HP: "+unit.hp()+"/"+unit.fullHp(),
					12,
					Color.BLACK,
					false,
					false);
			// damage
			drawText(
					g,
					new Position(xmid+100, 110),
					"DMG: "+unit.damage(),
					12,
					Color.BLACK,
					false,
					false);
			// race
			drawTextWithBackground(
					g,
					new Position(xmid-120, 120),
					unit.race().substring(0, 1).toUpperCase() + unit.race().substring(1),
					12,
					Color.BLACK,
					this.raceColors.get(unit.race)
			);
			// unit reach
			drawText(
					g,
					new Position(xmid+100, 130),
					"Dist: "+unit.reach(),
					12,
					(unit.reach() <= 4)?(new Color(200, 0, 0)):((unit.reach() >= 20)?new Color(0, 150, 0):Color.BLACK)
			);
			// list strengths
			int count = 0;
			for (Map.Entry<String, String> entry : unit.strengthList().entrySet()) {
				String strength = entry.getKey();
				drawText(
						g,
						new Position(xmid, 170 + (18*count)),
						strength.substring(0, 1).toUpperCase()+strength.substring(1),
						16,
						Color.black
				);
				count++;
			}
		}
	}
}
