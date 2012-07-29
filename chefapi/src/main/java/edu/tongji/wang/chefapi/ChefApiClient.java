package edu.tongji.wang.chefapi;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import edu.tong.wang.chefapi.method.Get;
import edu.tong.wang.chefapi.method.Post;

public class ChefApiClient {
	private String endpoint;
	private String userId;
	private String pemPath;
	
	/**
	 * 
	 * @param userId user name correspond to the pem key
	 * @param pemPath path of the auth key
	 * @param endpoint chef api server address
	 */
	public ChefApiClient(String userId, String pemPath, String endpoint){
		this.userId = userId;
		this.pemPath = pemPath;
		this.endpoint = endpoint;
	}
	
	/**
	 * 
	 * @param path in the endpoint. e.g /clients
	 * @return
	 */
	public Get get(String path){
		Get get = new Get(new GetMethod(endpoint+path));
		get.setPemPath(pemPath);
		get.setUserId(userId);
		return get;
	}
	
	public Post post(String path){
		Post post = new Post(new PostMethod(endpoint+path));
		post.setPemPath(pemPath);
		post.setUserId(userId);
		return post;
	}
	
	public Header[] buildHeaders(){
	    
	    return null;
	}
	
}