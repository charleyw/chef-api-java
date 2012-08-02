package com.ericsson.cloudready;

public class TestCls {
    
    public String startThread(){
        Runnable run = new Runnable() {
            
            public void run() {
                for(int i=0; i<10; i++){
                    try {
                        Thread.sleep(1000);
                        System.out.println("Sleepped 1s");
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
