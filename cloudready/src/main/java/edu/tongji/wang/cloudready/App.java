package edu.tongji.wang.cloudready;

import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Hello world!
 * 
 */
public class App {
    private static String opToken = "";
    private static Calendar tokenDate;
    public static void main(String[] args) {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DAY_OF_MONTH, -2);
        tokenDate = ca;
        getOpToken();
    }
    
    public static String getOpToken(){
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.HOUR_OF_DAY, -24);
        if(ca.after(tokenDate)){
            PostMethod post = new PostMethod("http://macloud.dnsdynamic.com:5000/v2.0/token");
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
                    opToken = o.get("xxx").getAsString();
                    tokenDate = Calendar.getInstance();
                }
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        return opToken;
    }
}
