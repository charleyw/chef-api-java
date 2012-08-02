package com.ericsson.cloudready.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ericsson.cloudready.model.Server;

public class ServerDAOFileImpl implements ServerDAO {
    private static String SPLIT = ",";
    private static String SERVER_DB_FILE = "serverDB.txt";
    private File dbFile;
    
    public ServerDAOFileImpl(){
        URL url = getClass().getResource("/"+SERVER_DB_FILE);
        
        dbFile = new File(url.getPath());
        
        if(!dbFile.exists()){
            try {
                if(!dbFile.createNewFile())
                throw new Throwable("Database file does not exist!");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public List<Server> getServerByIId(String instanceId) {
        List<Server> servers = new ArrayList<Server>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbFile));
            String line = null;
            while((line=br.readLine())!=null){
                String[] props = line.split(SPLIT);
                if(props[0].equals(instanceId)){
                    Server server = new Server(props[1], props[2]);
                    server.setIid(props[0]);
                    server.setIpaddress(props[3]);
                    server.setStatus(props[4]);
                    
                    servers.add(server);
                }
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
        return servers;
    }

    public void addServer(Server server) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(dbFile, true));
            StringBuilder sb = new StringBuilder();
            sb.append(server.getIid()).append(SPLIT);
            sb.append(server.getHostname()).append(SPLIT);
            sb.append(server.getId()).append(SPLIT);
            sb.append(server.getIpaddress()).append(SPLIT);
            sb.append(server.getStatus());
            bw.write(sb.toString());
            bw.newLine();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteServer(String id) {
        List<Server> servers = new ArrayList<Server>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbFile));
            String line = null;
            while((line=br.readLine())!=null){
                String[] props = line.split(SPLIT);
                //read all the file that do not match id
                if(!props[2].equals(id)){
                    Server server = new Server(props[1], props[2]);
                    server.setIid(props[0]);
                    server.setIpaddress(props[3]);
                    server.setStatus(props[4]);
                    
                    servers.add(server);
                }
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
        
        BufferedWriter bw = null;
        try {
            String path = dbFile.getPath();
            if(dbFile.delete()){
                dbFile = new File(path);
                dbFile.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(dbFile, true));
            for(Server s : servers){
                StringBuilder sb = new StringBuilder();
                sb.append(s.getIid()).append(SPLIT);
                sb.append(s.getHostname()).append(SPLIT);
                sb.append(s.getId()).append(SPLIT);
                sb.append(s.getIpaddress()).append(SPLIT);
                sb.append(s.getStatus());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw!=null)
                    bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteServerByIid(String instanceId) {
        List<Server> servers = new ArrayList<Server>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbFile));
            String line = null;
            while((line=br.readLine())!=null){
                String[] props = line.split(SPLIT);
                //read all the file that do not match id
                if(!props[0].equals(instanceId)){
                    Server server = new Server(props[1], props[2]);
                    server.setIid(props[0]);
                    server.setIpaddress(props[3]);
                    server.setStatus(props[4]);
                    
                    servers.add(server);
                }
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
        
        BufferedWriter bw = null;
        try {
            String path = dbFile.getPath();
            if(dbFile.delete()){
                dbFile = new File(path);
                dbFile.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(dbFile, true));
            for(Server s : servers){
                StringBuilder sb = new StringBuilder();
                sb.append(s.getIid()).append(SPLIT);
                sb.append(s.getHostname()).append(SPLIT);
                sb.append(s.getId()).append(SPLIT);
                sb.append(s.getIpaddress()).append(SPLIT);
                sb.append(s.getStatus());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw!=null)
                    bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
    }

}
