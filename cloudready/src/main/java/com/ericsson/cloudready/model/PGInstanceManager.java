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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cloudready.Utils;
import com.ericsson.cloudready.dao.InstanceDAO;
import com.ericsson.cloudready.dao.InstanceDAODBImpl;
import com.ericsson.cloudready.dao.InstanceDAOFileImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import edu.tongji.wang.chefapi.ChefApiClient;
import edu.tongji.wang.chefapi.method.ApiMethod;


public class PGInstanceManager extends InstanceManager {
    
    public static Logger LOG = LoggerFactory.getLogger(PGInstanceManager.class);
    
    private static String CHEF_DATA_BAG="instanceIds";
    
    @Override
    public Instance newInstance(String name, final String type) {
    	
    	final String iid = UUID.randomUUID().toString();
    	final Instance instance = new Instance(iid, name, type);
    	instance.setOwner("admin");
    	String serverName = "LOTC"+iid.substring(0, 4);
    	final String serverId = createServer(serverName);
    	
    	Runnable instanceTask = new Runnable() {
			
			public void run() {
			    LOG.debug("begin to request ip address from openstack");
			    boolean ipNotGot = true;
				for(int i=0;i<100 && ipNotGot;i++){
			        String token = getOpToken();
			        GetMethod get = new GetMethod("http://macloud.dnsdynamic.com:8774/v2/0ae541f8059b4f71ad90dc469316fc23/servers/"+serverId);
			        get.addRequestHeader("X-Auth-Token", token);
			        LOG.debug("request server ip with token: "+token);
			        HttpClient client = new HttpClient();
			        try {
			            int code = client.executeMethod(get);
			            LOG.debug("request ip response: ");
			            LOG.debug("\t"+code);
			            LOG.debug("\t"+get.getResponseBodyAsString());

			            if (code<400){
			                JsonParser parser = new JsonParser();
			                JsonObject o = (JsonObject)parser.parse(get.getResponseBodyAsString());
			                JsonObject serverO = o.getAsJsonObject("server");
			                JsonObject ipObj = serverO.getAsJsonObject("addresses");
			                JsonArray ipPrivateObj = null;
			                String ipaddress = null;
			                if(ipObj!=null){
			                    ipPrivateObj = ipObj.getAsJsonArray("private");
			                }else{
			                    LOG.debug("wait 2s for next try...");
			                    Thread.sleep(2000);
			                    continue;
			                }
			                
			                if(ipPrivateObj!=null){
			                    ipaddress = ipPrivateObj.get(0).getAsJsonObject().get("addr").getAsString();
			                }else{
			                    LOG.debug("wait 2s for next try...");
			                    Thread.sleep(2000);
			                    continue;
			                }
			                
			                LOG.debug("ipaddress: "+ipaddress);
			                String status = o.getAsJsonObject("server").get("status").getAsString();
			                LOG.debug("server status: "+status);
			                if(ipaddress!=null && !ipaddress.equals("")){
			                	String[] ips = ipaddress.split("\\.");
			                	String serverName = "node"+ips[2]+ips[3];
			                	Server server = new Server(serverName, serverId);
			                	server.setIid(iid);
			                	server.setIpaddress(ipaddress);
			                	server.setStatus(status);
			                	List<Server> servers = new ArrayList<Server>();
			                	servers.add(server);
			                	instance.setServers(servers);
			                	
			                	JsonObject obb = new JsonObject();
			                	obb.addProperty("id", serverName);
			                	obb.addProperty("instanceId", iid);
			                	obb.addProperty("reportUrl", "http://192.168.100.4:8080/cloudready/logs");
			                	LOG.debug("begin to create chef databag item: "+ obb.toString());
			                	createChefDataItem(serverName, obb.toString());
			                	
			                	LOG.debug("begin to create chef node: "+ serverName);
			                	NodeString ns = new NodeString(serverName);
			                	ns.addAttribute("instanceId", iid);
			                	ns.addAttribute("reportUrl", "http://192.168.100.4:8080/cloudready/logs");
			                	ns.addRunList("install_pg");
			                	ns.addRunList("install_license");
			                	ns.addRunList("install_pm");
			                	String reqBody = ns.build();
			                	LOG.debug("request body: "+ reqBody);
			                	createChefNode(serverName, reqBody);
			                	
			                	InstanceDAO dao = new InstanceDAODBImpl();
			                	dao.addInstance(instance);
			                	
			                	ipNotGot = false;
			                	return;
			                }
			                
			            }
			        } catch (HttpException e) {
			            e.printStackTrace();
			            ipNotGot = false;
			        } catch (IOException e) {
			            e.printStackTrace();
			            ipNotGot = false;
			        } catch (InterruptedException e) {
						e.printStackTrace();
						ipNotGot = false;
					} catch (Throwable e) {
                        e.printStackTrace();
                        ipNotGot = false;
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
    
    private boolean createChefDataItem(String id, String reqBody) {
        String pemPath = getClass().getResource("/wang.pem").getPath();
        LOG.debug("Get pem at: "+pemPath);
        ChefApiClient cac = new ChefApiClient("wang", pemPath, "http://macloud.dnsdynamic.com:4000");
        
        cac.delete("/data/instanceIds/"+id).execute();
        ApiMethod am = cac.post("/data/instanceIds").body(reqBody).execute();
        int code = am.getReturnCode();
        LOG.debug("return code: "+code+"\n"+am.getResponseBodyAsString());
        if(code>=400){
            LOG.error("create node failed:");
            LOG.error("return code: "+code);
            LOG.error("response: "+am.getResponseBodyAsString());
            return false;
            
        }        
        return true;
    }
    
    /**
     * 
     * @param nodeName node name in the chef server
     * @param type use to determine the role and run list of the node
     * @return
     * @throws Throwable 
     */
    private boolean createChefNode(String nodeName, String reqBody) throws Throwable{
        String pemPath = getClass().getResource("/wang.pem").getPath();
        LOG.debug("Get pem at: "+pemPath);
        ChefApiClient cac = new ChefApiClient("wang", pemPath, "http://macloud.dnsdynamic.com:4000");
        //delete all nodes and clients named nodeName before create
        cac.delete("/nodes/"+nodeName).execute();
        cac.delete("/clients/"+nodeName).execute();

        LOG.debug("creating node and request body is: "+reqBody);
        ApiMethod am = cac.post("/nodes").body(reqBody).execute();
        int code = am.getReturnCode();
        LOG.debug("return code: "+code+"\n"+am.getResponseBodyAsString());
        if(code>=400){
            LOG.error("create node failed:");
            LOG.error("return code: "+code);
            LOG.error("response: "+am.getResponseBodyAsString());
            return false;
            
        }
        return true;
    }
    
    /**
     *  create a server instance in openstack
     * @param serverName 
     * @return the id of the server in openstack
     */
    private String createServer(String serverName){
    	String serverID = "";
    	String postBody = "{ \"server\" : { \"name\" : \"SERVERNAME\", \"imageRef\" : \"6ac9ef8d-b8d3-4160-9a39-9d37f3b53510\", \"flavorRef\" : \"6\", \"metadata\" : { \"My Server Name\" : \"esvwyzv\" }}}";
    	postBody = postBody.replace("SERVERNAME", serverName);
        String token = getOpToken();
        PostMethod post = new PostMethod("http://macloud.dnsdynamic.com:8774/v2/0ae541f8059b4f71ad90dc469316fc23/servers");
        post.addRequestHeader("X-Auth-Token", token);
        post.addRequestHeader("Content-Type", "application/json");
        post.setRequestBody(postBody);
        HttpClient client = new HttpClient();
        try {
            LOG.debug("create server in openstack, using token: "+token);
            LOG.debug("request body: "+ postBody);
            int code = client.executeMethod(post);
            LOG.debug("openstack response: ");
            LOG.debug("\t"+code);
            LOG.debug("\t"+post.getResponseBodyAsString());
            if (code<400){
                JsonParser parser = new JsonParser();
                JsonObject o = (JsonObject)parser.parse(post.getResponseBodyAsString());
                //TODO correct the path for ipaddress
                serverID = o.getAsJsonObject("server").get("id").getAsString();
                LOG.debug("get server id: "+serverID);
            }else{
                try {
                    throw new Throwable("create server in openstack fialed");
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverID;   	
    }
    
    public String getOpToken(){
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
                //24hour
                if(now-time>86400011){
                    return Utils.getOpToken(file);
                }
                
                return ss[1];
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
        return null;
    }
    
    /**
     * request String of chef node
     * @author esvwyzv
     *
     */
    public static class NodeString{
        
        private JsonObject node;
        private JsonObject attr;
        private JsonObject over;
        private JsonObject defaults;
        private JsonArray runList;
        public NodeString(String nodeName){
            node = new JsonObject();
            node.addProperty("name", nodeName);
            node.addProperty("chef_type", "node");
            node.addProperty("json_class", "Chef::Node");
            attr = new JsonObject();
            defaults = new JsonObject();
            over = new JsonObject();
            runList = new JsonArray();
        }
        
        public NodeString addAttribute(String name, String value){
            attr.addProperty(name, value);
            return this;
        }
        
        public NodeString addRunList(String name){
            String recipe = "recipe["+name+"]";
            JsonPrimitive jp = new JsonPrimitive(recipe);
            runList.add(jp);
            return this;
        }
        
        public String build(){
            node.add("attributes", attr);
            node.add("run_list", runList);
            node.add("defaults", defaults);
            return node.toString();
        }
    }
    
}
