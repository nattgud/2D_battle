package repository;

import Util.DB;
import dto.UnitDTO;
import model.DBResult;

import java.util.ArrayList;
import java.util.HashMap;

public class UnitRepository {
	public static ArrayList<String> getAllNames() {
		ArrayList<String> monsternames = new ArrayList<>();
		DBResult monsternamesFromDB = DB.query("SELECT name FROM monsters ORDER BY id ASC;");
		for(DBResult data : monsternamesFromDB) {
			monsternames.add(data.getString("name"));
		}
		return monsternames;
	}
	public static UnitDTO loadUnitFromName(String monsterName) {
		try {
			DBResult inData = DB.query("SELECT monsters.id,monsters.name,monsters.distance,monsters.health,monsters.damage,monsters.ranged,monsters.flying, races.name AS race, races.strong AS raceStrength FROM monsters LEFT JOIN races ON races.id = monsters.race WHERE monsters.name = ? LIMIT 1;", monsterName.replaceAll(" ", "_"));
			for(DBResult data : inData) {
				String name = data.getString("name").replace("_", " ");
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				DBResult strengthsData = DB.query("SELECT strengths.name,(SELECT s2.name FROM strengths s2 WHERE s2.id = strengths.against LIMIT 1) AS against FROM monsters_strengths LEFT JOIN strengths ON strengths.id = monsters_strengths.strength WHERE monsters_strengths.monster = ?;", data.getInt("id"));
				HashMap<String, String> strengths = new HashMap<>();
				for (DBResult strengthData : strengthsData) {
					strengths.put(strengthData.getString("name"), strengthData.getString("against"));
				}
				return new UnitDTO(
						data.getInt("id"),
						name,
						data.getInt("health"),
						data.getInt("damage"),
						data.getInt("distance"),
						data.getString("race"),
						data.getString("raceStrength"),
						data.getBoolean("ranged"),
						data.getBoolean("flying"),
						strengths
				);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Unable to load unit "+monsterName);
		}
		return null;
	}
}
