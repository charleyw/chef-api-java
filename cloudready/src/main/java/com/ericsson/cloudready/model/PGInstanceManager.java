package com.ericsson.cloudready.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import com.ericsson.cloudready.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class PGInstanceManager extends InstanceManager {

    @Override
    public Instance newInstance(String type) {
        String token = getOpToken();
        PostMethod post = new PostMethod("http://macloud.dnsdynamic.com:5000/v2.0/servers");
        post.addRequestHeader("X-Auth-Token", token);
        HttpClient client = new HttpClient();
        try {
            int code = client.executeMethod(post);
            if (code==200){
                JsonParser parser = new JsonParser();
                JsonObject o = (JsonObject)parser.parse(post.getResponseBodyAsString());
                //TODO correct the path for ipaddress
                String ipAddr = o.get("ipaddr").getAsString();
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteInstance(String id) {
    }
    
    public String getOpToken(){
        String token = null;
        URL url = getClass().getResource("/token.txt");
        File file = new File(url.getPath());
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            if(line==null){
                return Utils.getOpToken(file);
            }else{
                String[] ss = line.split(":");
                long time = Long.valueOf(ss[0]);
                Date date = new Date();
                long now = date.getTime();
                if(now-time>86400011){
                    return Utils.getOpToken(file);
                }
                
                return ss[1];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
