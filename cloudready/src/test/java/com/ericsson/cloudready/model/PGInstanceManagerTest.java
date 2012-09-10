package com.ericsson.cloudready.model;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cloudready.model.PGInstanceManager.NodeString;

import edu.tongji.wang.chefapi.ChefApiClient;
import edu.tongji.wang.chefapi.method.ApiMethod;

public class PGInstanceManagerTest {
    
    public static Logger LOG = LoggerFactory.getLogger(PGInstanceManagerTest.class);
    
    @Test
    @Ignore
    public void testGetToken() {
        LOG.debug("begin to create chef node: ");
        NodeString ns = new NodeString("node111111");
        ns.addAttribute("instanceId", "node111111");
        ns.addAttribute("reportUrl", "node111111");
        String reqBody = ns.build();
        
        String pemPath = getClass().getResource("/wang.pem").getPath();
        LOG.debug("Get pem at: "+pemPath);
        ChefApiClient cac = new ChefApiClient("wang", pemPath, "http://macloud.dnsdynamic.com:4000");
        //delete all nodes and clients named nodeName before create
        cac.delete("/nodes/"+"node111111").execute();
        cac.delete("/clients/"+"node111111").execute();

        LOG.debug("creating node and request body is: \n\t"+reqBody);
        ApiMethod am = cac.post("/nodes").body(reqBody).execute();
        int code = am.getReturnCode();
        LOG.debug("return code: "+code+"\n\t"+am.getResponseBodyAsString());
    }

}
