package edu.tongji.wang.model;

import org.apache.commons.httpclient.methods.PostMethod;

import edu.tongji.wang.cloudready.App;

public class PGInstanceManager extends InstanceManager {

    @Override
    public Instance newInstance(String type) {
        String token = App.getOpToken();
        PostMethod post = new PostMethod("http://macloud.dnsdynamic.com:5000/v2.0/servers");
        post.addRequestHeader("X-Auth-Token", token);
        
        return null;
    }

    @Override
    public void deleteInstance(String id) {
    }

}
