package com.ericsson.cloudready.rest;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ericsson.cloudready.dao.InstanceDAO;
import com.ericsson.cloudready.dao.InstanceDAODBImpl;
import com.ericsson.cloudready.dao.InstanceDAOFileImpl;
import com.ericsson.cloudready.dao.LogDAO;
import com.ericsson.cloudready.dao.LogDAODBImpl;
import com.ericsson.cloudready.model.Instance;
import com.ericsson.cloudready.model.InstanceManager;
import com.ericsson.cloudready.model.LogMsg;
import com.ericsson.cloudready.model.PGInstanceManager;
import com.ericsson.cloudready.model.Server;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Path("/ins")
public class InstanceResource {
	
	@GET
	@Produces("application/json")
	public String getAllInstances(){
		InstanceDAO dao = new InstanceDAOFileImpl();
		List<Instance> instances = dao.getAllInstances();
		Gson gson = new Gson();
		return gson.toJson(instances);
	}
	
	@GET
	@Path("/{id}")
	public String getInstance(@PathParam("id") String id){
	    String html = "";
        
	    InstanceDAO dao = new InstanceDAODBImpl();
	    Instance in = dao.getInstanceById(id);
	    
	    LogDAO logDao = new LogDAODBImpl();
        List<LogMsg> logs =  logDao.getLogMsgByIid(id);
        List<Server> servers = in.getServers();
        Configuration config=new Configuration();
        String tmppath = getClass().getResource("/templates").getPath();
        try {
            config.setDirectoryForTemplateLoading(new File(tmppath));
            config.setObjectWrapper(new DefaultObjectWrapper());
            Template template=config.getTemplate("detail.ftl");
            Map<String, Object> root=new HashMap<String, Object>();
            root.put("servers",servers);
            root.put("logs",logs);
            StringWriter sw = new StringWriter();
            template.process(root , sw);
            html=sw.toString();
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return html;
	}
	 
	
	@POST
	@Produces("text/html")
	public String AddInstance(@FormParam("name") String name, @FormParam("type") String type){
		System.out.println("name:"+name+" type:"+type);
		InstanceManager im = new PGInstanceManager();
		im.newInstance(name, type);
		return getAllInstancesAsHtml();
	}
	
	@GET
	@Produces("text/html")
	public String getAllInstancesAsHtml(){
		InstanceDAO dao = new InstanceDAODBImpl();
		String html = "";
		List<Instance> instances = dao.getAllInstances();
		Configuration config=new Configuration();
		String tmppath = getClass().getResource("/templates").getPath();
		try {
			config.setDirectoryForTemplateLoading(new File(tmppath));
	    	config.setObjectWrapper(new DefaultObjectWrapper());
	    	Template template=config.getTemplate("list.ftl");
	    	Map<String, Object> root=new HashMap<String, Object>();
	    	root.put("instances",instances);
	    	StringWriter sw = new StringWriter();
	    	template.process(root , sw);
	    	html=sw.toString();
	    	sw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return html;
	}	
	
}
