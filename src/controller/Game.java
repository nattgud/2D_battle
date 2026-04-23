package controller;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import Util.DB;
import Util.Pathfinder;
import Util.Random;
import Util.Settings;
import model.*;
import repository.UnitRepository;
import service.Canvas;
import javax.swing.*;

public class Game {
	private GameRenderer gameRenderer;

	private final List<String> monsterNames = new ArrayList<>();
	private final List<Unit> units = new ArrayList<>();
	private final List<Obstacle> obstacles = new ArrayList<>();
	private final List<Background> backgrounds = new ArrayList<>();
	private final List<Particle> particles = new ArrayList<>();
	boolean playerTurn = true;
	private final Canvas canvas;
	private Position hoverPos = new Position(0, 0);
	private Unit selected;
	private boolean interactive = true;
	private Timer timer;
	private int state = 0;
	private String playerName = "Player";
	private ArrayList<SaveGame> saveGames = new ArrayList<>();

	public Game(Canvas canvas) {
		this.canvas = canvas;
		this.gameRenderer = new GameRenderer();
		for(int c = 0; c < 5; c++) {
			this.saveGames.add(new SaveGame(c));
		}
		for(SaveGame sg : this.saveGames)
			sg.updExists();

		for (int y = 0; y < Settings.height; y++)
			for (int x = 0; x < Settings.width; x++)
				backgrounds.add(new Background(new Position(x, y), Texture.randomType("grass")));

		gameRenderer.addButton(
				"beginbutton",
				new Position((Settings.width-4)/2, Settings.height-2).realPos(),
				"BEGIN"
		);
		for(int c = 0; c < 5; c++) {
			gameRenderer.addButton(
					"savebutton_" + c,
					new Position((Settings.width * Settings.scale / 2) - 120, 200 + (c * 60)),
					"Save " + (c + 1),
					24
			);
			gameRenderer.addButton(
					"loadbutton_" + c,
					new Position((Settings.width * Settings.scale / 2) + 20, 200 + (c * 60)),
					"Load " + (c + 1),
					24,
					!this.saveGames.get(c).exists()
			);
		}
		gameRenderer.addButton(
				"restartbutton",
				new Position((Settings.width * Settings.scale / 2) - 70, 500),
				"Restart",
				32
		);
		newGame();
	}
	private void newGame() {
		String input = JOptionPane.showInputDialog(null, "Spelarnamn: ");
		if (input != null && !input.isEmpty()) {
			this.playerName = input;
		}
		this.state = 0;
		this.selected = null;
		this.interactive = true;
		this.playerTurn = true;
		this.obstacles.clear();
		this.units.clear();
		for (int i = 0; i < Random.randomInt(40, 80); i++) {
			Position p;
			do { p = new Position(Random.randomInt(0, Settings.width - 1), Random.randomInt(0, Settings.height - 1)); }
			while (isOccupiedStart(p, obstacles));
			obstacles.add(new Obstacle(p, Texture.randomType("obstacle")));
		}
		generateUnits();
		this.canvas.render();
	}
	private void generateUnits() {
		try {
			this.monsterNames.clear();
			this.monsterNames.addAll(UnitRepository.getAllNames());
		} catch (Exception e) {
			System.out.println("Couldn't load units");
		}
		for(int c = 0; c < 10; c++)
			units.add(new Unit(
				new Position(2, 2 + c),
				this.monsterNames.get(Random.randomInt(0, this.monsterNames.size()-1)),
				"player"));
		for(int c = 0; c < 10; c++)
			units.add(new Unit(
					new Position(Settings.width - 3, 2 + c),
					this.monsterNames.get(Random.randomInt(0, this.monsterNames.size()-1)),
					"enemy"));
	}

	private boolean isOccupiedStart(Position pos, List<? extends GameObject> list) {
		return pos.x == 2 || pos.x == Settings.width - 3 || isOccupied(pos, list);
	}

	private boolean isOccupied(Position pos, List<? extends GameObject> list) {
		return list.stream().anyMatch(o -> o.pos().equals(pos));
	}

	private Pathfinder buildPathfinder() {
		return new Pathfinder(units, obstacles);
	}

	private boolean isWalkable(Position pos, Pathfinder pf) {
		return pf.canReach(selected, pos, false) && pf.distance(selected, pos) <= selected.reach();
	}

	private boolean canMelee(Unit target, Pathfinder pf) {
		return pf.distance(selected, target.pos()) <= selected.reach() + 1;
	}

	private List<Unit> aliveUnits() { return units.stream().filter(Unit::alive).toList(); }
	private List<Unit> aliveUnits(String team) { return units.stream().filter(u -> u.alive() && u.team().equals(team)).toList(); }

	private void nextTurn() {
		playerTurn = !playerTurn;
		interactive = playerTurn;
		selected = null;
		canvas.render();
		if (!playerTurn) computerMove();
	}

	private void handleRangedAttack(Unit attacker, Unit target) {
		interactive = false;
		String shotType = "arrow";
		if(attacker.race().equals("magic")) shotType = "magic";
		if(attacker.hasStrength("fire")) shotType = "fire";
		if(attacker.hasStrength("water")) shotType = "water";
		Particle arrow = new Particle(shotType, selected.pos().realPos(), target.pos().realPos(), 50);
		particles.add(arrow);
		timer = new Timer(1000 / 30, e -> {
			arrow.move();
			if (arrow.done()) {
				timer.stop();
				particles.remove(arrow);
				hurt(target, selected);
			}
			canvas.render();
		});
		timer.start();
	}
	private void hurt(Unit victim, Unit attacker) {
		boolean strongHurt = victim.hurt(attacker);
		Particle tempBlood = new Particle((strongHurt)?Texture.DAMAGE_STRONG:Texture.DAMAGE, victim.pos().realPos(), 20);
		if(attacker.race.equals("magic")) {
			tempBlood = new Particle((strongHurt)?Texture.DAMAGE_MAGIC_STRONG:Texture.DAMAGE_MAGIC, victim.pos().realPos(), 20);
		}
		if(attacker.race.equals("unholy") || attacker.hasStrength("unholy")) {
			tempBlood = new Particle((strongHurt)?Texture.DAMAGE_UNHOLY_STRONG:Texture.DAMAGE_UNHOLY, victim.pos().realPos(), 20);
		}
		System.out.println("hurt!");
		Particle blood = tempBlood;
		particles.add(blood);
		Particle blade = new Particle(Texture.ATTACK, attacker.pos().realPos(), 20);
		if(!attacker.isRanged()) particles.add(blade);
		timer = new Timer(1000/60, e -> {
			blood.live();
			blade.live();
			if(blade.done()) {
				particles.remove(blade);
			}
			if(blood.done()) {
				timer.stop();
				particles.remove(blood);
				nextTurn();
			}
			canvas.render();
		});
		timer.start();
	}

	private void handleMeleeAttack(Unit target, Pathfinder pf) {
		interactive = false;
		timer = new Timer(100, e -> {
			if (pf.distance(selected, target.pos()) > 1) {
				selected.moveTo(pf.nextStep(selected, target.pos()));
			} else {
				timer.stop();
				hurt(target, selected);
			}
			canvas.render();
		});
		timer.start();
	}

	private void handleMove(Position target, Pathfinder pf) {
		selected.target = target;
		interactive = false;
		timer = new Timer(100, e -> {
			if (pf.distance(selected, target) > 0) {
				selected.moveTo(pf.nextStep(selected, target, true));
			} else {
				timer.stop();
				nextTurn();
			}
			canvas.render();
		});
		timer.start();
	}

	private void handlePauseMenu() {
		for(int c = 0; c < 5; c++) {
			if(gameRenderer.mouseOverButton("savebutton_"+c, hoverPos)) {
				this.saveGames.get(c).setUnits(this.units);
				this.saveGames.get(c).setTurn(this.playerTurn);
				this.saveGames.get(c).setPlayerName(this.playerName);
				this.saveGames.get(c).writeToDB();
				for(SaveGame sg : this.saveGames)
					sg.updExists();
				Particle okIcon = new Particle(Texture.UI_YES, new Position(200, 100), 30);
				particles.add(okIcon);
				timer = new Timer(1000 / 30, e -> {
					okIcon.live();
					if (okIcon.done()) {
						timer.stop();
						particles.remove(okIcon);
					}
					canvas.render();
				});
				timer.start();
			}
			if(gameRenderer.mouseOverButton("loadbutton_"+c, hoverPos)) {
				this.saveGames.get(c).readFromDB();
				this.units.clear();
				this.units.addAll(this.saveGames.get(c).getUnits());
				this.playerTurn = this.saveGames.get(c).getTurn();
				this.playerName = this.saveGames.get(c).getPlayerName();
				this.state = 1;
				Particle okIcon = new Particle(Texture.UI_YES, new Position(200, 100), 30);
				particles.add(okIcon);
				timer = new Timer(1000 / 30, e -> {
					okIcon.live();
					if (okIcon.done()) {
						timer.stop();
						particles.remove(okIcon);
					}
					canvas.render();
				});
				timer.start();
				this.canvas.render();
			}
		}
		if(gameRenderer.mouseOverButton("restartbutton", hoverPos)) {
			newGame();
			Particle okIcon = new Particle(Texture.UI_YES, new Position(200, 100), 30);
			particles.add(okIcon);
			timer = new Timer(1000 / 30, e -> {
				okIcon.live();
				if (okIcon.done()) {
					timer.stop();
					particles.remove(okIcon);
				}
				canvas.render();
			});
			timer.start();
			this.canvas.render();
		}
	}

	public void keyPress(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(this.state == 1) {
				state = 3;
			} else {
				state = 1;
			}
			canvas.render();
		}
	}
	public boolean click(String type, Position clickPos, boolean userClick) {
		if(state == 2) {
			newGame();
			return false;
		}
		if(state == 3) {
			this.handlePauseMenu();
			return false;
		}
		if(state == 0) {
			if(gameRenderer.mouseOverButton("beginbutton", hoverPos)) state = 1;

			for(int i = 0; i < units.size(); i++) {
				Unit unit = units.get(i);
				if (!unit.isHovered(clickPos)) continue;
				int change = 1;
				if(type.equals("right")) change = -1;
				String newName = this.monsterNames.get(((unit.id()-1)+change+this.monsterNames.size())%this.monsterNames.size());
				units.set(i, new Unit(unit.pos(), newName, unit.team()));
			}
			canvas.render();
			return false;
		}

		if (!interactive && userClick) return false;
		if (userClick && !playerTurn) return false;

		boolean found = false;
		Pathfinder pf = buildPathfinder();

		for (Unit unit : aliveUnits()) {
			if (!unit.isHovered(clickPos)) continue;
			boolean own = unit.team().equals(playerTurn ? "player" : "enemy");
			boolean enemy = unit.team().equals(playerTurn ? "enemy" : "player");
			if (type.equals("left") && own) { selected = unit; found = true; }
			else if (type.equals("right") && selected != null && enemy) {
				if (selected.isRanged()) handleRangedAttack(selected, unit);
				else if (canMelee(unit, pf)) handleMeleeAttack(unit, pf);
				found = true;
			}
		}

		if (type.equals("right") && !found && selected != null) {
			Position target = Position.gamePos(clickPos);
			if (isWalkable(target, pf)) handleMove(target, pf);
		} else if (!found) {
			selected = null;
			return false;
		}
		canvas.render();
		return true;
	}

	private void computerMove() {
		canvas.render();
		Pathfinder pf = buildPathfinder();
		List<ResultUnit> targets = new ArrayList<>();

		for (Unit enemy : aliveUnits("enemy")) {
			for (Unit player : aliveUnits("player")) {
				int dist = pf.distance(enemy, player.pos());
				String type = enemy.isRanged() ? "range" : dist <= enemy.reach() ? "melee" : "walk";
				boolean melee = !enemy.isRanged();
				int reach = enemy.reach();
				int hp = player.hp();
				int maxHp = player.fullHp();
				int distScore = 1000 / (dist + 2);
				int inReachBonus = (melee && dist <= reach)
						? (reach + 1 - dist) * 20
						: 0;
				int damagePressure = (enemy.damage() * 100) / Math.max(1, hp);
				int rangePenalty = enemy.isRanged()
						? (int) ((Settings.width-dist)*2)
						: 0;
				int healthFactor = 50 + (hp * 50 / maxHp);
				int score =
						damagePressure +
								(distScore * 2) +
								inReachBonus -
								rangePenalty;

				score = (score * healthFactor) / 100;
				score += reach * 5;
				if(!pf.canReach(enemy, player.pos())) score = -100;
				targets.add(new ResultUnit(enemy, player, type, score));
			}
		}

		targets.sort((a, b) -> (int) (b.score - a.score));
		if (targets.isEmpty()) {
			System.out.println("computer passes");
			return;
		}

		ResultUnit best = targets.get(0);
		selected = best.unit;

		switch (best.type) {
			case "range" -> handleRangedAttack(selected, best.target);
			case "melee" -> handleMeleeAttack(best.target, pf);
			case "walk" -> handleMove(pf.stepTowards(selected, best.target.pos(), selected.reach()), pf);
			default -> nextTurn();
		}
		canvas.render();
	}

	public void hover(Position hoverPos) {
		if (!interactive) return;
		this.hoverPos = hoverPos;
		canvas.render();
	}

	public void draw(Graphics2D g) {
		gameRenderer.drawGameObjectList(g, backgrounds);
		gameRenderer.drawGameObjectList(g, obstacles);
		if(state == 3) {
			gameRenderer.setAlpha(g, 0.8f);
		} else {
			gameRenderer.setAlpha(g, 1f);
		}

		if (selected != null && playerTurn && state == 1) {
			Pathfinder pf = buildPathfinder();
			for (int y = 0; y < Settings.height; y++) {
				for (int x = 0; x < Settings.width; x++) {
					Position pos = new Position(x, y);
					boolean walkable = isWalkable(pos, pf);
					boolean hovered = Math.round(hoverPos.x / Settings.scale) == x && Math.round(hoverPos.y / Settings.scale) == y;
					if (walkable) {
						gameRenderer.drawRectangle(
								g,
								new Position(x, y).realPos(),
								new Position(Settings.scale, Settings.scale),
								hovered ? new Color(100, 255, 100, 100) : new Color(0, 255, 0, 50),
								null
						);
					}
					gameRenderer.drawRectangle(
							g,
							new Position(x, y).realPos(),
							new Position(Settings.scale, Settings.scale),
							null,
							new Color(0, 0, 0, 50)
					);
				}
			}
		}
		if(this.state == 1) {
			for (Unit unit : this.aliveUnits()) {
				Color col = Color.BLACK;
				if (!unit.isHovered(hoverPos)) continue;
				if (unit.team().equals("player")) col = Color.GREEN;
				else if (unit.team().equals("enemy") && selected != null) {
					if (selected.raceStrength().equals(unit.race())) col = Color.ORANGE;
					else col = Color.RED;
				} else continue;
				gameRenderer.drawRectangle(
						g,
						new Position(unit.pos().x, unit.pos().y).realPos(),
						new Position(Settings.scale, Settings.scale),
						null,
						col
				);
			}
		}

		for (Unit unit : units) {
			gameRenderer.drawGameObject(g, unit);
			if (selected == unit && state == 1) {
				gameRenderer.drawRectangle(
						g,
						new Position(unit.pos().x, unit.pos().y).realPos(),
						new Position(Settings.scale, Settings.scale),
						null,
						Color.GREEN
				);
			}
		}
		if(state == 0) {
			if(this.hoverPos.x >= Settings.scale*2 && this.hoverPos.x < Settings.scale*3) {
				for (int index = 2; index < 12; index++) {
					if(this.hoverPos.y >= Settings.scale*index && this.hoverPos.y < Settings.scale*(index+1)) {
						g.drawRect((Settings.scale*2), (Settings.scale*index), Settings.scale, Settings.scale);
						gameRenderer.drawRectangle(
								g,
								new Position(2, index).realPos(),
								new Position(Settings.scale, Settings.scale),
								null,
								Color.BLUE
						);
						break;
					}
				}
			}

			gameRenderer.drawButton("beginbutton", g, this.hoverPos);
		}

		if(state != 2) {
			gameRenderer.drawParticleList(g, particles);
			if(this.state == 3) {
				gameRenderer.setAlpha(g, 1f);
				gameRenderer.drawRectangle(g,
						new Position((Settings.width*Settings.scale/2)-200, 50),
						new Position(400, (Settings.height*Settings.scale)-200),
						Color.WHITE,
						null
				);
				gameRenderer.drawText(
						g,
						new Position((Settings.width * Settings.scale) / 2, 130),
						"PAUSE",
						72
				);
				int x, y;

				for(int c = 0; c < 5; c++) {
					gameRenderer.drawButton("savebutton_"+c, g, hoverPos);
					gameRenderer.drawButton("loadbutton_"+c, g, hoverPos);
				}
				gameRenderer.drawButton("restartbutton", g, hoverPos);
				return;
			}
			g.setFont(new Font("Arial", Font.BOLD, 16));
			for (Unit unit : units) {
				//unit.drawUI(g, unit.isHovered(hoverPos), this.state == 0 ? 1:2);
				gameRenderer.drawUI(g, unit, unit.isHovered(hoverPos), this.state == 0 ? 1:2);	// Fixa!
			}
		}
		if(this.state == 0)
			return;

		gameRenderer.drawOutlinedText(g, playerTurn ? this.playerName : "Computer", 5, 25, 24, Color.WHITE);

		int playerScore = aliveUnits("player").size();
		int enemyScore = aliveUnits("enemy").size();

		if (playerScore == 0 || enemyScore == 0) state = 2;

		if(state == 2) {
			gameRenderer.drawTextWithBackground(
					g,
					new Position(Settings.width/2,
					Settings.height/2).realPos(),
					playerScore == 0 && enemyScore == 0 ? "DRAW!" : playerScore == 0 ? "LOSER!" : "WINNER!",
					48
			);
			this.interactive = false;
		}
	}

}