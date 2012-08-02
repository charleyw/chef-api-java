package com.ericsson.cloudready.dao;

import java.util.List;

public interface LogDAO {
    public List<String> getLogByIId(String instanceId);
    public void info(String log);
    public void clearLog();
}
