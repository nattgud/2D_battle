package service;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import controller.Game;
import model.Position;

// Drawable canvas
public class Canvas extends java.awt.Canvas {
	public Canvas() { setBackground(Color.BLACK); }
	// game handler
	private Game game;
	public void init() {
		// init game handler, connect canvas
		this.game = new Game(this);
		// init mouse listeners, connect to game
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
		// render first frame
		this.render();
	}

	// render new frame
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw game frame
		this.game.draw(g2d);

		g2d.dispose();
		bs.show();
	}
}
