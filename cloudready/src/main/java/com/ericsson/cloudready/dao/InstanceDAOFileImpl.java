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
import java.util.LinkedList;
import java.util.List;

import com.ericsson.cloudready.model.Instance;
import com.ericsson.cloudready.model.Server;


public class InstanceDAOFileImpl implements InstanceDAO {
    
    private File dbFile;
    private String DB_FILE_NAME = "instance_db.txt";
    private static String SPLIT = ",";
    
    public InstanceDAOFileImpl(){
        URL url = getClass().getResource("/"+DB_FILE_NAME);
        
        dbFile = new File(url.getPath());
        
        if(!dbFile.exists()){
        }
        
    }

    public List<Instance> getAllInstances() {
        List<Instance> result = new ArrayList<Instance>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbFile));
            String line = null;
           
            ServerDAO serverDAO = new ServerDAOFileImpl();
            while((line=br.readLine())!=null){
                String[] props = line.split(SPLIT);
                Instance instance = new Instance(props[0], props[1], props[2]);
                instance.setOwner(props[3]);
                List<Server> servers = serverDAO.getServerByIId(props[0]);
                instance.setServers(servers);
                result.add(instance);
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

    public Instance getInstanceById(String id) {
        Instance result = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbFile));
            String line = null;
            ServerDAO serverDAO = new ServerDAOFileImpl();
            while((line=br.readLine())!=null){
                String[] props = line.split(SPLIT);
                if(id.equals(props[0])){
                    result = new Instance(id, props[1], props[2]);
                    result.setOwner(props[3]);
                    List<Server> servers = serverDAO.getServerByIId(props[0]);
                    result.setServers(servers);
                    break;
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
        return result;
    }

    public List<Instance> getInstancesByUser(String userId) {
        List<Instance> result = new LinkedList<Instance>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbFile));
            String line = null;
            ServerDAO serverDAO = new ServerDAOFileImpl();
            while((line=br.readLine())!=null){
                String[] props = line.split(SPLIT);
                if(userId.equals(props[3])){
                    Instance instance = new Instance(props[0], props[1], props[2]);
                    instance.setOwner(props[3]);
                    List<Server> servers = serverDAO.getServerByIId(props[0]);
                    instance.setServers(servers);
                    result.add(instance);
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
        return result;
    }

    public void deleteInstance(String id) {
        List<Instance> instances = new ArrayList<Instance>();
        ServerDAO serverDAO = new ServerDAOFileImpl();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbFile));
            String line = null;
            while((line=br.readLine())!=null){
                String[] props = line.split(SPLIT);
                if(!id.equals(props[0])){
                    Instance ins = new Instance(props[0], props[1], props[2]);
                    ins.setOwner(props[3]);
                    instances.add(ins);
                }else{
                    serverDAO.deleteServerByIid(id);
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
            for (Instance ins : instances){
                StringBuilder sb = new StringBuilder();
                sb.append(ins.getId()).append(SPLIT);
                sb.append(ins.getName()).append(SPLIT);
                sb.append(ins.getType()).append(SPLIT);
                sb.append(ins.getOwner());
                bw.write(sb.toString());
                bw.newLine();
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public Instance addInstance(Instance instance) {
        BufferedWriter bw = null;
        ServerDAO serverDAO = new ServerDAOFileImpl();
        try {
            bw = new BufferedWriter(new FileWriter(dbFile, true));
            StringBuilder sb = new StringBuilder();
            sb.append(instance.getId()).append(SPLIT);
            sb.append(instance.getName()).append(SPLIT);
            sb.append(instance.getType()).append(SPLIT);
            sb.append(instance.getOwner());
            bw.write(sb.toString());
            bw.newLine();
            for(Server s : instance.getServers()){
                serverDAO.addServer(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public Instance updateInstance(Instance instance) {
        // TODO Auto-generated method stub
        return null;
    }

}
