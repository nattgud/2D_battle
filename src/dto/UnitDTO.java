package dto;

import model.Position;
import repository.UnitRepository;

import java.util.HashMap;

public class UnitDTO {
	public int id;
	public String name;
	public int health;
	public int team;
	public Position pos;

	public final String race;
	public final String raceStrength;
	public final int distance;
	public final int damage;
	public final boolean flying;
	public final boolean ranged;
	public final HashMap<String, String> strengths;
	public UnitDTO(int id, String name, int health, int fullhealth, int team, int x, int y) {
		this.id = id;
		this.name = name;
		this.health = health;
		this.team = team;
		this.pos = new Position(x, y);

		this.race = null;
		this.raceStrength = null;
		this.ranged = false;
		this.flying = false;
		this.strengths = null;
		this.damage = 0;
		this.distance = 0;
	}
	public UnitDTO(int id, String name, int health, int damage, int distance, String race, String RaceStrength, boolean ranged, boolean flying, HashMap<String, String> strengths) {
		this.id = id;
		this.name = name;
		this.health = health;
		this.damage = damage;
		this.distance = distance;
		this.race = race;
		this.raceStrength = RaceStrength;
		this.ranged = ranged;
		this.flying = flying;
		this.strengths = strengths;
	}
	public static UnitDTO UnitDTOFromDB(String name) {
		return UnitRepository.loadUnitFromName(name);
	}
}
