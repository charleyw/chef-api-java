package com.ericsson.cloudready.dao;

import java.util.List;

import com.ericsson.cloudready.model.LogMsg;

public interface LogDAO {
//    public List<String> getLogByIId(String instanceId);
//    public void info(String log);
//    public void clearLog();
    public void log(String iid, String msg);
    public List<LogMsg> getLogMsgByIid(String instanceId);
    public void deleteLogMsgsByIid(String instanceId);
}
