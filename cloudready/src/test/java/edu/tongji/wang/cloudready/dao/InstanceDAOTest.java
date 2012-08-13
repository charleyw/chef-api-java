package edu.tongji.wang.cloudready.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;

import com.ericsson.cloudready.dao.InstanceDAO;
import com.ericsson.cloudready.dao.InstanceDAODBImpl;
import com.ericsson.cloudready.dao.InstanceDAOFileImpl;
import com.ericsson.cloudready.model.Instance;
import com.ericsson.cloudready.model.Server;

public class InstanceDAOTest {

    @Test
    public void testAddInstance() {
//        InstanceDAO dao = new InstanceDAOFileImpl();
    	InstanceDAO dao = new InstanceDAODBImpl();
    	final String iid = UUID.randomUUID().toString();
        Instance in = new Instance(iid, "PG1", "PGSA");
        in.setOwner("admin");
        Server server = new Server("node1005", "akdflksdkasdfklsda");
        server.setIid(in.getId());
        server.setIpaddress("192.168.100.1");
        server.setStatus("OK");
        List<Server> servers = new ArrayList<Server>();
        servers.add(server);
        
        in.setServers(servers);
        
        dao.addInstance(in);
    }
    
    @Test
    public void testGetAllInstance(){
        InstanceDAO dao = new InstanceDAODBImpl();
        List<Instance> ins = dao.getAllInstances();
        
        for(Instance in : ins){
            System.out.println("id:"+in.getId());
            for(Server s : in.getServers()){
            	System.out.println("\tserverId:"+s.getId());	
            }
            
        }
    }
    
    @Test
    public void testDeleteInstance(){
        InstanceDAO dao = new InstanceDAODBImpl();
        
        dao.deleteInstance("PG1");
    }

}
