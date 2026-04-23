package Util;

import model.Obstacle;
import model.Position;
import model.Unit;

import java.util.*;

public class Pathfinder {
	private final int width, height;
	private List<Unit> units;
	private List<Obstacle> obstacles;

	public Pathfinder(List<Unit> units, List<Obstacle> obstacles) {
		this.width = Settings.width;
		this.height = Settings.height;
		this.units = units;
		this.obstacles = obstacles;
	}

	public List<Position> findPath(Unit startUnit, Position goal) {
		return this.findPath(startUnit, goal, true);
	}
	public List<Position> findPath(Unit startUnit, Position goal, boolean ignoreGoal) {
		Position start = startUnit.pos();
		Set<Position> blocked = new HashSet<>();

		for (Unit u : units) {
			if (!u.pos().equals(start)) {
				blocked.add(u.pos());
			}
		}

		for (Obstacle o : obstacles) {
			blocked.add(o.pos());
		}
		boolean flying = startUnit.isFlying();

		class Node {
			Position p; int g, f;
			Node(Position p, int g, int f) { this.p = p; this.g = g; this.f = f; }
		}

		PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
		Map<Position, Position> cameFrom = new HashMap<>();
		Map<Position, Integer> gScore = new HashMap<>();
		Set<Position> visited = new HashSet<>();

		gScore.put(start, 0);
		open.add(new Node(start, 0, heuristic(start, goal)));

		while (!open.isEmpty()) {
			Node current = open.poll();

			if (!visited.add(current.p)) continue;

			if (current.p.equals(goal)) {
				List<Position> path = new ArrayList<>();
				for (Position p = goal; p != null; p = cameFrom.get(p)) path.add(p);
				Collections.reverse(path);
				return path;
			}

			for (Position n : neighbors(current.p)) {
				boolean isGoal = n.equals(goal);
				Position prev = current.p;
				boolean enteringGoal = n.equals(goal);
				if (ignoreGoal && enteringGoal && blocked.contains(prev)) {
					continue;
				}
				boolean passable;
				if (flying) {
					passable = ignoreGoal || !isGoal || !blocked.contains(n);
				} else {
					passable = !blocked.contains(n) || (isGoal && ignoreGoal);
				}
				if (!passable) continue;

				int tentative = gScore.getOrDefault(current.p, Integer.MAX_VALUE) + 1;

				if (tentative < gScore.getOrDefault(n, Integer.MAX_VALUE)) {
					cameFrom.put(n, current.p);
					gScore.put(n, tentative);
					open.add(new Node(n, tentative, tentative + heuristic(n, goal)));
				}
			}
		}

		return Collections.emptyList();
	}

	public Position nextStep(Unit startUnit, Position goal) {
		List<Position> path = findPath(startUnit, goal);
		return path.size() > 1 ? path.get(1) : null;
	}
	public Position nextStep(Unit startUnit, Position goal, boolean ignoreGoal) {
		List<Position> path = findPath(startUnit, goal, ignoreGoal);
		return path.size() > 1 ? path.get(1) : null;
	}

	public boolean canReach(Unit startUnit, Position goal) {
		return !findPath(startUnit, goal).isEmpty();
	}
	public boolean canReach(Unit startUnit, Position goal, boolean ignoreGoal) {
		return !findPath(startUnit, goal, ignoreGoal).isEmpty();
	}

	private int heuristic(Position a, Position b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	private List<Position> neighbors(Position p) {
		return List.of(
				new Position(p.x + 1, p.y),
				new Position(p.x - 1, p.y),
				new Position(p.x, p.y + 1),
				new Position(p.x, p.y - 1)
		).stream().filter(this::inBounds).toList();
	}

	private boolean inBounds(Position p) {
		return p.x >= 0 && p.y >= 0 && p.x < width && p.y < height;
	}
	public int distance(Unit startUnit, Position goal) {
		List<Position> path = findPath(startUnit, goal);
		return path.isEmpty() ? -1 : path.size() - 1;
	}
	public Position stepTowards(Unit startUnit, Position goal, int steps) {
		List<Position> path = findPath(startUnit, goal);
		if (path.isEmpty()) return null;
		int index = Math.min(steps, path.size() - 1);
		return path.get(index);
	}
}