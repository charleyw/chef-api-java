package com.ericsson.cloudready.model;

public abstract class InstanceManager {
	public InstanceManager(){
		
	}
	
	public abstract Instance newInstance(String type);
	public abstract void deleteInstance(String id);
}
