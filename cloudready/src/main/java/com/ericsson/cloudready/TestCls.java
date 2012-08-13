package com.ericsson.cloudready;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCls {
    private final static Logger logger = LoggerFactory.getLogger(TestCls.class); 
    public String startThread(){
        Runnable run = new Runnable() {
            
            public void run() {
                for(int i=0; i<10; i++){
                    try {
                        Thread.sleep(1000);
                        System.out.println("Sleepped 1s");
                        logger.info("Sleepped 1s");
                        logger.debug("Sleepped 1s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(run).start();
        return "started";
    }
}
