package com.ericsson.cloudready.model;

public class LogMsg {
    
    private String logTime;
    private String logMsg;
    private String loggerId;
    
    public LogMsg(String iid, String time, String msg){
        logTime = time;
        logMsg = msg;
        loggerId = iid;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public String getLoggerId() {
        return loggerId;
    }

    public void setLoggerId(String loggerId) {
        this.loggerId = loggerId;
    }

    
}
