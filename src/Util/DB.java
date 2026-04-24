package Util;

import model.DBResult;

import java.sql.*;

public class DB {
	private static final String URL = "jdbc:mysql://localhost:3306/battle_game";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static Connection conn;

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	public static void init() {
		try {
			conn = getConnection();
			System.out.println("- Ansluten till databas");
		} catch (SQLException e) {
			System.err.println("- Anslutning misslyckades: " + e.getMessage());
		}
	}
	public static DBResult query(String sql, Object... params) {
		DBResult results = new DBResult();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
			try (ResultSet rs = ps.executeQuery()) {
				ResultSetMetaData meta = rs.getMetaData();
				while (rs.next()) {
					DBResult row = new DBResult();
					for (int i = 1; i <= meta.getColumnCount(); i++)
						row.put(meta.getColumnLabel(i), rs.getObject(i));
					results.add(row);
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
		return results;
	}
	public static int update(String sql, Object... params) {
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			for (int i = 0; i < params.length; i++) stmt.setObject(i + 1, params[i]);
			int affectedRows = stmt.executeUpdate();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) return rs.getInt(1);
			}
			return affectedRows;
		} catch (Exception e) {
			System.out.println("Något gick fel. " + e.getMessage());
			return -1;
		}
	}
	public static int delete(String sql, Object... params) {
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) stmt.setObject(i + 1, params[i]);
			return stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Något gick fel. " + e.getMessage());
			return -1;
		}
	}
	public static void beginTransaction() {
		try {
			conn.setAutoCommit(false);
		} catch (Exception e) {
			System.out.println("Något gick fel");
		}
	}
	public static void commit() {
		try {
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			System.out.println("Något gick fel");
		}
	}
	public static void rollback() {
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			System.out.println("Något gick fel");
		}
	}
}
