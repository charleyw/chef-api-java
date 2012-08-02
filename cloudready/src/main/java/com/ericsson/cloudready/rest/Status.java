package com.ericsson.cloudready.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/status")
public class Status {
    
    @GET
    @Produces("text/html")
    public String getStatus(){
        
        return "<h1>test</h1>";
    }
    
    @GET
    @Produces("application/json")
    public String getStatusAsJson(){
        return "{\"name\":\"xxx\"}";
    }
}
