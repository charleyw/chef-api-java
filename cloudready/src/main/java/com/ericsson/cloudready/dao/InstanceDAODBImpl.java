package com.ericsson.cloudready.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ericsson.cloudready.model.Instance;
import com.ericsson.cloudready.model.Server;

public class InstanceDAODBImpl implements InstanceDAO {
	
    private static String TABLE_NAME = "instances";
	
	public InstanceDAODBImpl(){
		
	}

	public Instance addInstance(Instance instance) {
		PreparedStatement stmtInsert = null;
		Connection conn = null;
		try {
			Class.forName(Constants.DRIVER);

			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
			
			StringBuilder sbInsert = new StringBuilder();
			sbInsert.append("INSERT INTO ");
			sbInsert.append(TABLE_NAME);
			sbInsert.append(" (Id, name, type, owner, status) ");
			sbInsert.append(" VALUES (?, ?, ?, ?, ?) ");
			
			stmtInsert = conn.prepareStatement(sbInsert.toString());
			stmtInsert.setString(1, instance.getId());
			stmtInsert.setString(2, instance.getName());
			stmtInsert.setString(3, instance.getType());
			//TODO add user support
			stmtInsert.setInt(4, 0);
			stmtInsert.setString(5, Constants.IN_SPAWNING);
			
			ServerDAO serverDao = new ServerDAODBImpl();
			for(Server server : instance.getServers()){
				serverDao.addServer(server);	
			}
			
			
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
		return instance;
	}

	public Instance updateInstance(Instance instance) {
        PreparedStatement stmtInsert = null;
        Connection conn = null;
        try {
            Class.forName(Constants.DRIVER);

            conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
            
            StringBuilder sbInsert = new StringBuilder();
            sbInsert.append("UPDATE ");
            sbInsert.append(TABLE_NAME);
            sbInsert.append(" SET name=?, type=?, owner=?, status=? ");
            sbInsert.append("WHERE Id=?");
            
            stmtInsert = conn.prepareStatement(sbInsert.toString());
            stmtInsert.setString(1, instance.getName());
            stmtInsert.setString(2, instance.getType());
            stmtInsert.setInt(3, 0);
            //TODO add user support
            stmtInsert.setString(4, instance.getStatus());
            stmtInsert.setString(5, instance.getId());
            
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
        return instance;
	}

	public List<Instance> getAllInstances() {
		PreparedStatement stmtSelect = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Instance> instances = new ArrayList<Instance>();
		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
			
			StringBuilder sbSelect = new StringBuilder();
			sbSelect.append("SELECT * FROM ");
			sbSelect.append(TABLE_NAME);
			
			stmtSelect = conn.prepareStatement(sbSelect.toString());
			
			rs = stmtSelect.executeQuery();
			
			ServerDAO dao = new ServerDAODBImpl();
			while(rs.next()){
				Instance instance = new Instance(rs.getString("Id"), rs.getString("name"), rs.getString("type"));
				instance.setOwner("admin");
				instance.setStatus(rs.getString("status"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startTime = sdf.format(rs.getTimestamp("startTime"));
				instance.setStartTime(startTime);
				List<Server> servers = dao.getServerByIId(instance.getId());
				instance.setServers(servers);
				instances.add(instance);
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
		return instances;	
	}

	public Instance getInstanceById(String id) {
		PreparedStatement stmtSelect = null;
		Connection conn = null;
		ResultSet rs = null;
		Instance instance = null;
		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
			
			StringBuilder sbSelect = new StringBuilder();
			sbSelect.append("SELECT * FROM ");
			sbSelect.append(TABLE_NAME);
			sbSelect.append(" WHERE Id=?");
			
			stmtSelect = conn.prepareStatement(sbSelect.toString());
			stmtSelect.setString(1, id);
			
			rs = stmtSelect.executeQuery();
			
			ServerDAO dao = new ServerDAODBImpl();
			while(rs.next()){
				instance = new Instance(rs.getString("Id"), rs.getString("name"), rs.getString("type"));
				instance.setOwner("admin");
				instance.setStatus(rs.getString("status"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startTime = sdf.format(rs.getTimestamp("startTime"));
				instance.setStartTime(startTime);
				List<Server> servers = dao.getServerByIId(instance.getId());
				instance.setServers(servers);
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
		return instance;	
	}

	public List<Instance> getInstancesByUser(String userId) {
		return null;
	}

	public void deleteInstance(String id) {
		PreparedStatement stmtSelect = null;
		Connection conn = null;
		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
			
			StringBuilder sbSelect = new StringBuilder();
			sbSelect.append("DELETE FROM ");
			sbSelect.append(TABLE_NAME);
			sbSelect.append(" WHERE Id=?");
			
			stmtSelect = conn.prepareStatement(sbSelect.toString());
			stmtSelect.setString(1, id);
			
			stmtSelect.execute();
			
			ServerDAO dao = new ServerDAODBImpl();
			dao.deleteServerByIid(id);
			
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
