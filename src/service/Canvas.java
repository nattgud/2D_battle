package service;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import Util.Settings;
import controller.Game;
import model.GameRenderer;
import model.Position;

public class Canvas extends java.awt.Canvas {
	public Canvas() { setBackground(Color.BLACK); }
	private Game game;
	public void init() {
		this.game = new Game(this);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					game.click("left", new Position(e.getX(), e.getY()), true);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					game.click("right", new Position(e.getX(), e.getY()), true);
				}
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
			game.hover(new Position(e.getX(), e.getY()));
			}
		});
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				game.keyPress(e);
			}
		});
		this.render();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		g2d.setColor(Color.BLACK); g2d.fillRect(0,0, Settings.width, Settings.height);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		this.game.draw(g2d);

		g2d.dispose();
		bs.show();
	}
}
