package edu.tong.wang.chefapi.method;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PutMethod;

public class Put extends ApiMethod{

	public Put(HttpMethod method) {
		super("PUT");
		this.method = method;
	}
	
	public ApiMethod body(String body){
		PutMethod put = (PutMethod) method;
		put.setRequestBody(body);
		return this;
	}

}
