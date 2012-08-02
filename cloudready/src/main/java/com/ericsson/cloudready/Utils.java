package com.ericsson.cloudready;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Hello world!
 * 
 */
public class Utils {
    private static String opToken = "";
    private static Calendar tokenDate;
    
    public static String getToken(){
        
        return null;
    }
    
    @SuppressWarnings("deprecation")
    public static String getOpToken(File file){

            PostMethod post = new PostMethod("http://macloud.dnsdynamic.com:5000/v2.0/tokens");
            String reqBody = "{ \"auth\":{ \"passwordCredentials\":{ \"username\":\"adminUser\", \"password\":\"123123\" }, \"tenantName\":\"admin\" } }";
            post.addRequestHeader("Content-Type", "application/json");
            post.setRequestBody(reqBody);
            HttpClient client = new HttpClient();
            try {
                int code = client.executeMethod(post);
                if (code == 200){
                    JsonParser parser = new JsonParser();
                    JsonObject o = (JsonObject)parser.parse(post.getResponseBodyAsString());
                    //TODO edit for the correct path
                    JsonObject jsele = o.getAsJsonObject("access").getAsJsonObject("token");
                    String token = jsele.get("id").getAsString();
                    opToken = token;
                    tokenDate = Calendar.getInstance();
                }
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(file));
                Date date = new Date();
                long now = date.getTime();
                bw.write(now+":"+opToken);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        return opToken;
    }
}
