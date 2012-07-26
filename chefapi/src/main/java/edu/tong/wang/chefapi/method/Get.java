package edu.tong.wang.chefapi.method;

import org.apache.commons.httpclient.HttpMethod;

public class Get extends ApiMethod{

	public Get(HttpMethod method) {
		super("GET");
		this.method = method;
	}

}
