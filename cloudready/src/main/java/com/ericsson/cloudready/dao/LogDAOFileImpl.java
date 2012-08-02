package com.ericsson.cloudready.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogDAOFileImpl implements LogDAO {
    
    private static String LOG_FOLDER="logs";
    private File dbFile = null;
    
    public LogDAOFileImpl(String id){
        URL url = getClass().getResource("/"+LOG_FOLDER);
        dbFile = new File(url.getPath()+"/"+id+".txt");
        
        if(!dbFile.exists()){
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public List<String> getLogByIId(String instanceId) {
        List<String> result = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbFile));
            String line = null;
            while((line=br.readLine())!=null){
                result.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void info(String log) {
        BufferedWriter bw = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            bw = new BufferedWriter(new FileWriter(dbFile, true));
            StringBuilder sb = new StringBuilder();
            sb.append(date).append(" ").append(log);
            bw.write(sb.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    public void clearLog() {
        if (dbFile.exists()){
            dbFile.delete();
        }
    }

}
