package edu.tongji.wang.cloudready.dao;

import java.util.List;

import org.junit.Test;

import com.ericsson.cloudready.dao.ServerDAO;
import com.ericsson.cloudready.dao.ServerDAOFileImpl;
import com.ericsson.cloudready.model.Server;

public class ServerDAOTest {

    @Test
    public void testAddServer() {
        try {
            ServerDAO dao = new ServerDAOFileImpl();
            Server server = new Server("node1", "openstackID");
            server.setIpaddress("192.168.0.1");
            server.setIid("instanceID");
            server.setStatus("Building");
            dao.addServer(server);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetAllServers(){
        try {
            ServerDAO dao = new ServerDAOFileImpl();
            List<Server> servers = dao.getServerByIId("instanceID");
            for(Server s : servers){
                System.out.println("name:" + s.getHostname());
            }
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDeleteServer(){
        try {
            ServerDAO dao = new ServerDAOFileImpl();
            dao.deleteServer("123");
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
