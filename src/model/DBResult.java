package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBResult extends ArrayList<DBResult> {
	private final Map<String, Object> data = new HashMap<>();

	public String getString(String key) { return (String) data.get(key); }
	public int getInt(String key) { return (int) data.get(key); }
	public boolean getBoolean(String key) { return (boolean) data.get(key); }
	public Object get(String key) { return data.get(key); }
	public void put(String key, Object value) { data.put(key, value); }
	public String toString() {
		return this.data.toString();
	}
}
