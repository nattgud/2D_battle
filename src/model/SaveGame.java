package model;

import Util.DB;
import dto.UnitDTO;

import java.util.ArrayList;
import java.util.List;

public class SaveGame {
	private boolean turn;
	private boolean exists = false;
	private int id;
	private String playerName;
	private List<UnitDTO> units = new ArrayList<>();
	public SaveGame(int id) {
		this.id = id;
	}
	public void readFromDB() {
		try {
			if(!this.updExists()) throw new Exception("Game doesn't exist");
			this.exists = true;
			DBResult dbData = DB.query("SELECT turn, playername FROM games WHERE id = ?;", this.id);
			for(DBResult data : dbData) {
				this.turn = data.getInt("turn")==0;
				this.playerName = data.getString("playername");
			}
			this.getUnitsFromDB();
		} catch(Exception e) {
			System.out.println("couldn't load saved game. "+e.getMessage());
		}
	}
	public boolean exists() {
		return this.exists;
	}
	public boolean updExists() {
		boolean found = false;
		try {
			DBResult dbData = DB.query("SELECT id FROM games WHERE id = ?;", this.id);
			found = (dbData.size() > 0);
		} catch(Exception e) {
			System.out.println("NO GAME");
		}

		this.exists = found;
		return found;
	}
	public void setTurn(boolean playerTurn) {
		this.turn = playerTurn;
	}
	public boolean getTurn() {
		return this.turn;
	}
	public String getPlayerName() {
		return this.playerName;
	}
	public void setPlayerName(String newName) {
		this.playerName = newName;
	}
	public void writeToDB() {
		try {
			DB.beginTransaction();
			if(!this.updExists()) {
				int ok = DB.update("INSERT INTO games(id, turn, playername) VALUES(?, ?, ?);", this.id, this.turn ? 0 : 1, this.playerName);
				if (ok == -1)
					throw new Exception("unable to create new game (" + this.id + ", " + String.valueOf(this.turn ? 0 : 1) + ").");
			} else {
				int ok = DB.update("UPDATE games SET turn = ?, playername = ? WHERE id = ?;", this.turn ? 0 : 1, this.playerName, this.id);
				if (ok == -1)
					throw new Exception("unable to create new game (" + this.id + ", " + String.valueOf(this.turn ? 0 : 1) + ").");
			}
			int affectedRows = DB.delete("DELETE FROM game_units WHERE game = ?;", this.id);
			if(affectedRows == -1) throw new Exception("Unable to clear units from previous game.");
			DBResult dbData;
			for(UnitDTO unit:this.units) {
				int ok = DB.update("INSERT INTO game_units(game, monster, team, x, y, health) VALUES(?, ?, ?, ?, ?, ?);", this.id, unit.id, unit.team, unit.pos.x, unit.pos.y, unit.health);
				if(ok == -1) throw new Exception("Unable to add unit to DB.");
			}
			DB.commit();
			this.exists = true;
			this.getUnitsFromDB();
		} catch(Exception e) {
			DB.rollback();
			e.printStackTrace();
			System.out.println("couldn't save game. "+e.getMessage());
		}
	}
	private void getUnitsFromDB() {
		this.units.clear();
		DBResult dbData = DB.query("SELECT gu.*,monsters.name AS name,monsters.health AS fullhealth,gu.health AS health FROM game_units gu INNER JOIN monsters ON monsters.id = gu.monster WHERE gu.game = ?;", this.id);
		for(DBResult data : dbData) {
			this.units.add(new UnitDTO(
					data.getInt("monster"),
					data.getString("name"),
					data.getInt("health"),
					data.getInt("fullhealth"),
					data.getInt("team"),
					data.getInt("x"),
					data.getInt("y")
			));
		}
	}
	public List<Unit> getUnits() {
		return this.units.stream().map(u -> new Unit(u.pos, u.name, (u.team==0)?"player":"enemy", u.health)).toList();
	}
	public void setUnits(List<Unit> units) {
		this.units =  new ArrayList<>(units.stream().map(u -> new UnitDTO(u.id(), u.name(), u.hp(), u.fullHp(), u.team().equals("player")?0:1, u.pos().x, u.pos().y)).toList());
	}
}