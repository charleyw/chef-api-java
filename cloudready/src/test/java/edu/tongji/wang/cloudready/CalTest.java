package edu.tongji.wang.cloudready;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

public class CalTest extends TestCase {
    @Test
    public void testCal(){
        Calendar ca = Calendar.getInstance();
        System.out.println("now:"+ca.getTime());
        ca.add(Calendar.HOUR_OF_DAY, -24);
        System.out.println("-25:"+ca.getTime());
        if(ca.before(Calendar.getInstance())){
            System.out.println("-24:"+ca.getTime());
        }
        
        Date lastday = ca.getTime();
        Date today = Calendar.getInstance().getTime();
        System.out.println("l:"+lastday.getTime());
        System.out.println("t:"+today.getTime());
        
    }
}
