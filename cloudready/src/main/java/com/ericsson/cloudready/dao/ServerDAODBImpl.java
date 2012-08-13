package com.ericsson.cloudready.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ericsson.cloudready.model.Server;

public class ServerDAODBImpl implements ServerDAO {

	private static String TABLE_NAME = "servers";

	public List<Server> getServerByIId(String instanceId) {
		PreparedStatement stmtSelect = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Server> servers = new ArrayList<Server>();
		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
			
			StringBuilder sbSelect = new StringBuilder();
			sbSelect.append("SELECT * FROM ");
			sbSelect.append(TABLE_NAME);
			sbSelect.append(" WHERE instanceId=?");
			
			stmtSelect = conn.prepareStatement(sbSelect.toString());
			stmtSelect.setString(1, instanceId);
			
			rs = stmtSelect.executeQuery();
			
			while(rs.next()){
				Server server = new Server(rs.getString("hostname"), rs.getString("opstkId"));
				server.setIid(rs.getString("instanceId"));
				server.setIpaddress(rs.getString("ipaddr"));
				server.setStatus(rs.getString("status"));
				servers.add(server);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return servers;	
	}

	public void addServer(Server server) {
		PreparedStatement stmtInsert = null;
		Connection conn = null;
		try {
			Class.forName(Constants.DRIVER);

			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);

			StringBuilder sbInsert = new StringBuilder();
			sbInsert.append("INSERT INTO ");
			sbInsert.append(TABLE_NAME);
			sbInsert.append(" (opstkId, instanceId, hostname, ipaddr, status) ");
			sbInsert.append(" VALUES (?, ?, ?, ?, ?) ");

			stmtInsert = conn.prepareStatement(sbInsert.toString());
			stmtInsert.setString(1, server.getId());
			stmtInsert.setString(2, server.getIid());
			stmtInsert.setString(3, server.getHostname());
			stmtInsert.setString(4, server.getIpaddress());
			stmtInsert.setString(5, server.getStatus());

			stmtInsert.execute();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteServer(String id) {
		PreparedStatement stmtDelete = null;
		Connection conn = null;
		try {
			Class.forName(Constants.DRIVER);

			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);

			StringBuilder sbInsert = new StringBuilder();
			sbInsert.append("DELETE FROM ");
			sbInsert.append(TABLE_NAME);
			sbInsert.append(" WHERE opstkId=?");

			stmtDelete = conn.prepareStatement(sbInsert.toString());
			stmtDelete.setString(1, id);

			stmtDelete.execute();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteServerByIid(String instanceId) {
		PreparedStatement stmtDelete = null;
		Connection conn = null;
		try {
			Class.forName(Constants.DRIVER);

			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
			StringBuilder sbInsert = new StringBuilder();
			sbInsert.append("DELETE FROM ");
			sbInsert.append(TABLE_NAME);
			sbInsert.append(" WHERE instanceId=?");

			stmtDelete = conn.prepareStatement(sbInsert.toString());
			stmtDelete.setString(1, instanceId);

			stmtDelete.execute();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

}
