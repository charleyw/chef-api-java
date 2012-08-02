package com.ericsson.cloudready.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.ericsson.cloudready.Utils;
import com.ericsson.cloudready.dao.InstanceDAO;
import com.ericsson.cloudready.dao.InstanceDAOFileImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class PGInstanceManager extends InstanceManager {

    @Override
    public Instance newInstance(String name, String type) {
    	final String serverId = getServer();
    	final String iid = UUID.randomUUID().toString();
    	final Instance instance = new Instance(iid, name, type);
    	instance.setOwner("admin");

    	
    	Runnable instanceTask = new Runnable() {
			
			public void run() {
				for(int i=0;i<100;i++){
			        String token = getOpToken();
			        GetMethod get = new GetMethod("http://macloud.dnsdynamic.com:5000/v2.0/servers/"+serverId);
			        get.addRequestHeader("X-Auth-Token", token);
			        HttpClient client = new HttpClient();
			        try {
			            int code = client.executeMethod(get);
			            if (code==200){
			                JsonParser parser = new JsonParser();
			                JsonObject o = (JsonObject)parser.parse(get.getResponseBodyAsString());
			                String ipaddress = o.getAsJsonObject("server").getAsJsonObject("addresses").getAsJsonArray("private").get(0).getAsJsonObject().get("addr").getAsString();
			                String status = o.getAsJsonObject("server").getAsJsonObject("status").getAsString();
			                if(ipaddress!=null && !ipaddress.equals("")){
			                	String[] ips = ipaddress.split(".");
			                	String serverName = "node"+ips[2]+ips[3];
			                	Server server = new Server(serverName, serverId);
			                	server.setIid(iid);
			                	server.setIpaddress(ipaddress);
			                	server.setStatus(status);
			                	List<Server> servers = new ArrayList<Server>();
			                	servers.add(server);
			                	instance.setServers(servers);
			                	
			                	//TODO add chef invoke, this req string should change with the type parameter
			                	String chefNodeStr = "{  \"name\": \"latte\",  \"chef_type\": \"NODENAME\",  \"json_class\": \"Chef::Node\",  \"attributes\": { \"hardware_type\": \"laptop\" },  \"overrides\": {  },  \"defaults\": {  },  \"run_list\": [ \"recipe[install_pg]\",\"recipe[install_license]\",\"recipe[install_pm]\" ] }";
			                	
			                	InstanceDAO dao = new InstanceDAOFileImpl();
			                	dao.addInstance(instance);
			                	break;
			                }else{
			                	Thread.sleep(1000);
			                }
			            }
			        } catch (HttpException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } catch (InterruptedException e) {
						e.printStackTrace();
					}					
				}
			}
		};
		
		new Thread(instanceTask).start();
    	
    	return instance;
    }

    @Override
    public void deleteInstance(String id) {
    }
    
    private String getServer(){
    	String serverID = "";
    	
        String token = getOpToken();
        PostMethod post = new PostMethod("http://macloud.dnsdynamic.com:5000/v2.0/servers");
        post.addRequestHeader("X-Auth-Token", token);
        HttpClient client = new HttpClient();
        try {
            int code = client.executeMethod(post);
            if (code==200){
                JsonParser parser = new JsonParser();
                JsonObject o = (JsonObject)parser.parse(post.getResponseBodyAsString());
                //TODO correct the path for ipaddress
                serverID = o.getAsJsonObject("server").get("id").getAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverID;   	
    }
    
    public String getOpToken(){
        String token = null;
        URL url = getClass().getResource("/token.txt");
        File file = new File(url.getPath());
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            if(line==null){
                return Utils.getOpToken(file);
            }else{
                String[] ss = line.split(":");
                long time = Long.valueOf(ss[0]);
                Date date = new Date();
                long now = date.getTime();
                if(now-time>86400011){
                    return Utils.getOpToken(file);
                }
                
                return ss[1];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
