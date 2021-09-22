package edu.tongji.wang.chefapi.method;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;

import edu.tongji.wang.chefapi.Utils;

public class ApiMethod {
	private HttpClient client = null;
	protected HttpMethod method = null;
	protected String reqBody = "";
	protected String userId = "";
	protected String pemPath = "";
	private String methodName = "GET";
	
	private int returnCode;
	
	public ApiMethod(String methodName){
		client = new HttpClient();
		this.methodName = methodName;
	}
	
	public ApiMethod execute(){
		String hashedPath = Utils.sha1AndBase64(method.getPath());
		String hashedBody = Utils.sha1AndBase64(reqBody);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String timeStamp = sdf.format(new Date());
		timeStamp = timeStamp.replace(" ", "T");
		timeStamp = timeStamp + "Z";
		
		StringBuilder sb = new StringBuilder();
		sb.append("Method:").append(methodName).append("\n");
		sb.append("Hashed Path:").append(hashedPath).append("\n");
		sb.append("X-Ops-Content-Hash:").append(hashedBody).append("\n");
		sb.append("X-Ops-Timestamp:").append(timeStamp).append("\n");
		sb.append("X-Ops-UserId:").append(userId);
		
		String auth_String = Utils.signWithRSA(sb.toString(), pemPath);
		String[] auth_headers = Utils.splitAs60(auth_String);
		
		method.addRequestHeader("Content-type", "application/json");
		method.addRequestHeader("X-Ops-Timestamp", timeStamp);
		method.addRequestHeader("X-Ops-Userid", userId);
		method.addRequestHeader("X-Chef-Version", "0.10.4");
		method.addRequestHeader("Accept", "application/json");
		method.addRequestHeader("X-Ops-Content-Hash", hashedBody);
		method.addRequestHeader("X-Ops-Sign", "version=1.0");


		for (int i = 0; i < auth_headers.length; i++) {
			method.addRequestHeader("X-Ops-Authorization-" + (i + 1), auth_headers[i]);
		}
		
		returnCode = 0;
		try {
			returnCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public void setHeaders(Header[] headers){
	    for(Header header : headers){
	        this.method.addRequestHeader(header);
	    }
	}
	
	public String getResponseBodyAsString(){
		String reqBody = null;
		try {
			reqBody = method.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			method.releaseConnection();
		}
		return reqBody;
	}
	
	public int getReturnCode(){
		return returnCode;
	}

	public String getReqBody() {
		return reqBody;
	}

	public void setReqBody(String body) {
		this.reqBody = body;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPemPath() {
		return pemPath;
	}

	public void setPemPath(String pemPath) {
		this.pemPath = pemPath;
	}
}
