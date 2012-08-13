package edu.tongji.wang.cloudready.dao;

import java.util.List;

import org.junit.Test;

import com.ericsson.cloudready.dao.LogDAO;
import com.ericsson.cloudready.dao.LogDAODBImpl;
import com.ericsson.cloudready.model.LogMsg;

public class LogDAOTest {
    
    @Test
    public void testLog(){
        LogDAO dao = new LogDAODBImpl();
        for (int i = 0; i < 10; i++) {
            dao.log("test"+ i, "testsatset");
        }
        
    }
    
    @Test
    public void testGetLog(){
        LogDAO dao = new LogDAODBImpl();
        List<LogMsg> logs = dao.getLogMsgByIid("test1");
        for (LogMsg log : logs) {
            System.out.println("id:"+log.getLoggerId()+" time:"+log.getLogTime()+" msg:"+log.getLogMsg());
        }
    }
}
