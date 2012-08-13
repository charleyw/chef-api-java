package com.ericsson.cloudready;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class TestLog {

    /**
     * @param args
     */
    public static void main(String[] args) {
        PostMethod post = new PostMethod("http://192.168.100.4:8080/cloudready/logs");
        post.setRequestBody("{\"instanceId\":\"test2\", \"msg\":\"test\"}");
        post.addRequestHeader("Content-Type", "applicaiton/json");
        HttpClient client = new HttpClient();
        try {
            client.executeMethod(post);
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
