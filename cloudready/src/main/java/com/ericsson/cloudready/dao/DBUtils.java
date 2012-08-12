package com.ericsson.cloudready.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtils {
	private static String FRAMEWORK = "embedded";
	private static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String PROTOCOL = "jdbc:derby:";

	private static String TABLE_NAME = "servers";

	private static String dbName = "derbyDB"; // the name of the database

	public static void setupTables() {
		Statement stmtCreate = null;
		Connection conn = null;
		try {
			Class.forName(DRIVER);

			Properties props = new Properties();
			props.put("user", "user1");
			props.put("password", "user1");

			conn = DriverManager.getConnection(PROTOCOL + dbName
					+ ";create=true", props);

			StringBuilder sbCreate = new StringBuilder();
			sbCreate.append("CREATE TABLE instances(");
			sbCreate.append(" Id varchar(50) NOT NULL default '',");
			sbCreate.append(" name varchar(255) default NULL,");
			sbCreate.append(" type varchar(255) default NULL,");
			sbCreate.append(" owner int(11) default NULL,");
			sbCreate.append(" status varchar(255) default NULL,");
			sbCreate.append(" startTime timestamp NULL default CURRENT_TIMESTAMP)");
//			sbCreate.append("PRIMARY KEY ('Id'))");
//			sbCreate.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
			
//			sbCreate.append("DROP TABLE instances");
//			sbCreate.append("CREATE TABLE servers(");
//			sbCreate.append(" Id int(11) NOT NULL auto_increment,");
//			sbCreate.append(" opstkId varchar(255) default NULL,");
//			sbCreate.append(" instanceId varchar(50) default NULL,");
//			sbCreate.append(" hostname varchar(255) default NULL)");
//			sbCreate.append(" ipaddr varchar(255) default NULL,");
//			sbCreate.append(" status varchar(255) default NULL,");
//			sbCreate.append("PRIMARY KEY ( Id )");
//			sbCreate.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
			
			stmtCreate = conn.createStatement();
			System.out.println(sbCreate.toString());
			stmtCreate.execute(sbCreate.toString());

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
	
	public static void main(String[] args){
		setupTables();
	}
}
