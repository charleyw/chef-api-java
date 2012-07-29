package edu.tongji.wang.model;

public abstract class InstanceManager {
	public InstanceManager(){
		
	}
	
	public abstract Instance newInstance();
	public abstract void deleteInstance();
}
