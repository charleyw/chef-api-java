package com.ericsson.cloudready.model;

import java.util.List;
import java.util.UUID;

public class Instance {
	private String id;
	private String type;
	private String name;
	private String owner;
//	public String 
	private List<Server> servers;
	
	public Instance(String id, String name, String type){
		this.type = type;
		this.name = name;
		this.id = id;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
	
}
