package com.ericsson.cloudready;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cloudready.model.PGInstanceManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Test {

    public static Logger LOG = LoggerFactory.getLogger(Test.class);
    /**
     * @param args
     */
    public static void main(String[] args) {
//        String token = "66472a8d7a9c4a7ca5127778c424081a";
//        GetMethod get = new GetMethod("http://macloud.dnsdynamic.com:8774/v2/0ae541f8059b4f71ad90dc469316fc23/servers/3a55a788-6b5c-4f47-8d13-6a3e5ec7880e");
//        get.addRequestHeader("X-Auth-Token", token);
//        LOG.debug("request server ip with token: "+token);
//        HttpClient client = new HttpClient();
//        try {
//            int code = client.executeMethod(get);
//            LOG.debug("request ip response: ");
//            LOG.debug("\t"+code);
//            LOG.debug("\t"+get.getResponseBodyAsString());
//
//            if (code<400){
//                JsonParser parser = new JsonParser();
//                JsonObject o = (JsonObject)parser.parse(get.getResponseBodyAsString());
//                String ipaddress = o.getAsJsonObject("server").getAsJsonObject("addresses").getAsJsonArray("private").get(0).getAsJsonObject().get("addr").getAsString();
//                LOG.debug("ipaddress: "+ipaddress);
//                String status = o.getAsJsonObject("server").get("status").getAsString();
//                LOG.debug("server status: "+status);
//
//            }
//        } catch (HttpException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }   
        PGInstanceManager pm = new PGInstanceManager();
        pm.newInstance("FirstTestxxx", "PG-standalone");
    }

}
