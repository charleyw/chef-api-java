package edu.tongji.wang.cloudready.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.ericsson.cloudready.dao.InstanceDAO;
import com.ericsson.cloudready.dao.InstanceDAOFileImpl;
import com.ericsson.cloudready.model.Instance;
import com.ericsson.cloudready.model.Server;

public class InstanceDAOTest {

    @Test
    @Ignore
    public void testAddInstance() {
        InstanceDAO dao = new InstanceDAOFileImpl();
        Instance in = new Instance("PG1", "PG1", "PGSA");
        in.setOwner("admin");
        Server server = new Server("node1005", "akdflksdkasdfklsda");
        server.setIid("PG1");
        server.setIpaddress("xxxx");
        server.setStatus("OK");
        List<Server> servers = new ArrayList<Server>();
        servers.add(server);
        
        in.setServers(servers);
        
        dao.addInstance(in);
    }
    
    @Test
    @Ignore
    public void testGetAllInstance(){
        InstanceDAO dao = new InstanceDAOFileImpl();
        List<Instance> ins = dao.getAllInstances();
        
        for(Instance in : ins){
            System.out.println("id:"+in.getId());
        }
    }
    
    @Test
    public void testDeleteInstance(){
        InstanceDAO dao = new InstanceDAOFileImpl();
        
        dao.deleteInstance("PG1");
    }

}
