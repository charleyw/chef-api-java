package com.ericsson.cloudready.rest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ericsson.cloudready.dao.InstanceDAO;
import com.ericsson.cloudready.dao.InstanceDAOFileImpl;
import com.ericsson.cloudready.model.Instance;
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
	@Produces("text/html")
	public String getAllInstancesAsHtml(){
		InstanceDAO dao = new InstanceDAOFileImpl();
		String html = "";
		List<Instance> instances = dao.getAllInstances();
		Configuration config=new Configuration();
		String tmppath = getClass().getResource("/templates").getPath();
		try {
			config.setDirectoryForTemplateLoading(new File(tmppath));
	    	config.setObjectWrapper(new DefaultObjectWrapper());
	    	Template template=config.getTemplate("test.ftl");
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
