package com.ericsson.cloudready.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ericsson.cloudready.model.LogMsg;

public class LogDAODBImpl implements LogDAO{
    private static String TABLE_NAME = "logs";
    
    public void log(String iid, String msg) {
        PreparedStatement stmtInsert = null;
        Connection conn = null;
        try {
            Class.forName(Constants.DRIVER);

            conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
            
            StringBuilder sbInsert = new StringBuilder();
            sbInsert.append("INSERT INTO ");
            sbInsert.append(TABLE_NAME);
            sbInsert.append(" (loggerId, msg) ");
            sbInsert.append(" VALUES (?, ?) ");
            
            stmtInsert = conn.prepareStatement(sbInsert.toString());
            stmtInsert.setString(1, iid);
            stmtInsert.setString(2, msg);
     
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

    public List<LogMsg> getLogMsgByIid(String instanceId) {
        PreparedStatement stmtSelect = null;
        Connection conn = null;
        ResultSet rs = null;
        List<LogMsg> logs = new ArrayList<LogMsg>();
        try {
            Class.forName(Constants.DRIVER);
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
            
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.append("SELECT * FROM ");
            sbSelect.append(TABLE_NAME);
            sbSelect.append(" WHERE loggerId=?");
            
            stmtSelect = conn.prepareStatement(sbSelect.toString());
            stmtSelect.setString(1, instanceId);
            
            rs = stmtSelect.executeQuery();
            
            while(rs.next()){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(rs.getTimestamp("logTime"));
                LogMsg logMsg = new LogMsg(rs.getString("loggerId"), time, rs.getString("msg"));
                logs.add(logMsg);
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
        return logs; 
    }

    public void deleteLogMsgsByIid(String instanceId) {
        PreparedStatement stmtSelect = null;
        Connection conn = null;
        try {
            Class.forName(Constants.DRIVER);
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PWD);
            
            StringBuilder sbSelect = new StringBuilder();
            sbSelect.append("DELETE FROM ");
            sbSelect.append(TABLE_NAME);
            sbSelect.append(" WHERE loggerId=?");
            
            stmtSelect = conn.prepareStatement(sbSelect.toString());
            stmtSelect.setString(1, instanceId);
            
            stmtSelect.execute();
            

            
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
