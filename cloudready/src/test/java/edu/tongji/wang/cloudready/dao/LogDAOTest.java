package edu.tongji.wang.cloudready.dao;

import java.util.List;

import org.junit.Test;

import com.ericsson.cloudready.dao.LogDAO;
import com.ericsson.cloudready.dao.LogDAOFileImpl;

public class LogDAOTest {
    
    @Test
    public void testLog(){
        LogDAO dao = new LogDAOFileImpl("PG1");
        dao.info("tes2t");
        dao.info("tes2t2");
        dao.info("test");
        dao.info("te2st");
        dao.info("tes2t");
        dao.info("tes222t");
        dao.info("te2st");
    }
    
    @Test
    public void testGetLog(){
        LogDAO dao = new LogDAOFileImpl("PG1");
        List<String> ss = dao.getLogByIId("PG1");
        for(String s : ss){
            System.out.println(s);
        }
    }
}
