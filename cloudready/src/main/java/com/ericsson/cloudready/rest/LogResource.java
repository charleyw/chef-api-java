package com.ericsson.cloudready.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cloudready.dao.LogDAO;
import com.ericsson.cloudready.dao.LogDAODBImpl;
import com.ericsson.cloudready.model.LogMsg;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/logs")
public class LogResource {
    
    public static Logger LOG = LoggerFactory.getLogger(LogResource.class);
    
    @POST
    public String log(String req){
        LOG.debug("request message: "+req);
        JsonParser parser = new JsonParser();
        JsonObject o = (JsonObject)parser.parse(req);
        String iid = o.get("instanceId").getAsString();
        String message = o.get("msg").getAsString();
        
        LOG.debug("Log instanceId:"+iid+", msg:"+message);
        LogDAO dao = new LogDAODBImpl();
        dao.log(iid, message);
        
        return null;
    }
    
    @GET
    @Path("/{id}")
    public String getLogInfo(@PathParam("id") String id){
        LogDAO dao = new LogDAODBImpl();
        List<LogMsg> logs = dao.getLogMsgByIid(id);
        Gson gson = new Gson();
        return gson.toJson(logs);
    }
}
