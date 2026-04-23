package model;

import Util.DB;
import Util.Random;
import Util.Settings;
import dto.UnitDTO;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Unit extends GameObject {
	private int monsterID;
	private String team;
	public Position target;
	private int distance;
	private String name;
	private int health;
	private int fullHealth;
	private int damage;
	public String race;
	private String raceStrength;
	private boolean flying;
	private boolean ranged;
	private HashMap<String, String> strengths = new HashMap<>();
	public Unit(Position pos, String monsterName, String team) {
		this.pos = pos;
		this.team = team;
		updateData(monsterName, -1);
	}
	public Unit(Position pos, String monsterName, String team, int hp) {
		this.pos = pos;
		this.team = team;
		updateData(monsterName, hp);
	}
	private void updateData(String unitName, int overrideHp) {
		UnitDTO source = UnitDTO.UnitDTOFromDB(unitName);
		this.monsterID = source.id;
		this.name = source.name;
		this.race = source.race;
		this.health = (overrideHp == -1)?source.health:overrideHp;
		this.fullHealth = source.health;
		this.texture = Texture.valueOf("UNIT_" + source.race.toUpperCase() + "_" + source.name.toUpperCase().replaceAll(" ", "_"));
		if(this.health <= 0) {
			this.texture = Texture.GRAVE;
		}
		this.distance = source.distance;
		this.damage = source.damage;
		this.raceStrength = source.raceStrength;
		this.flying = source.flying;
		this.ranged = source.ranged;
	}
	public int id() {
		return this.monsterID;
	}
	public boolean isFlying() {
		return this.flying;
	}
	public boolean isRanged() {
		return this.ranged;
	}
	public int reach() {
		return this.distance;
	}
	public String team() {
		return this.team;
	}
	public String name() {
		return this.name;
	}
	public String race() {
		return this.race;
	}
	public String raceStrength() {
		return this.raceStrength;
	}
	public boolean isStrongAgainst(String find) {
		return this.strengths.containsValue(find);
	}
	public boolean hasStrength(String find) {
		return this.strengths.containsKey(find);
	}
	public int numberOfStrengths() {
		return this.strengths.size();
	}
	public HashMap<String, String> strengthList() {
		return this.strengths;
	}
	public int damage() {
		return this.damage;
	}
	public int hp() {
		return this.health;
	}
	public int fullHp() {
		return this.fullHealth;
	}
	public boolean alive() { return this.health > 0; }
	public boolean hurt(Unit attacker) {
		double modifier = 1;
		int strengthDamage = 0;
		for (Map.Entry<String, String> entry : this.strengths.entrySet()) {
			strengthDamage += attacker.isStrongAgainst(entry.getKey())?25:0;
		}
		if(attacker.raceStrength().equals(this.race)) modifier = 2;
		if(Random.randomInt(0, 10) == 0) modifier += 1;
		this.health -= (int) ((attacker.damage()+strengthDamage) * modifier);
		if(this.health <= 0) {
			this.health = 0;
			this.texture = Texture.GRAVE;
		}
		return modifier != 1;
	}
}
