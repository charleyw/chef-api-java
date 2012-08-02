package edu.tongji.wang.cloudready;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import com.ericsson.cloudready.dao.InstanceDAO;
import com.ericsson.cloudready.dao.InstanceDAOFileImpl;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CalTest extends TestCase {
    @Test
    @Ignore
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
    
    @Test
    public void testFreeMarker(){
    	Configuration config=new Configuration();
    	String tmppath = getClass().getResource("/templates").getPath();
    	try {
			config.setDirectoryForTemplateLoading(new File(tmppath));
	    	config.setObjectWrapper(new DefaultObjectWrapper());
	    	Template template=config.getTemplate("test.ftl");
	    	Writer out=new OutputStreamWriter(System.out);
	    	Map<String, Object> root=new HashMap<String, Object>();
	    	InstanceDAO dao = new InstanceDAOFileImpl();
	    	 root.put("instances",dao.getAllInstances());
	    	template.process(root , out);
	    	out.flush();
	    	out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} 
        
    }
}
