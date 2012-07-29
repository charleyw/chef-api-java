package edu.tongji.wang.model;

public class PGInstance extends Instance {
	
	public static class PGInstanceManager extends InstanceManager{

		@Override
		public Instance newInstance() {
			
			return null;
		}

		@Override
		public void deleteInstance() {
		}
		
	}

	public PGInstance(String name, String type, String owner) {
		super(name, type, owner);
	}

}
