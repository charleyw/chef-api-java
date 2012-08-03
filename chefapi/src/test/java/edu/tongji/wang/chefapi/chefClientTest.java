package edu.tongji.wang.chefapi;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.tongji.wang.chefapi.method.ApiMethod;

public class chefClientTest extends TestCase {
	
	@Test
	public void testGet(){
		ChefApiClient cac = new ChefApiClient("wang", "C:/Users/ESVWYZV/wang.pem", "http://macloud.dnsdynamic.com:4000");
////		int code = cac.get("/clients").execute();
//		ApiMethod am = cac.get("/clients");
//		int code = am.execute();
//		String body = am.getResponseBodyAsString();
//		System.out.println(code+"  \n"+body);
		Gson gson = new Gson();
		JsonObject js = new JsonObject();
		js.addProperty("name", "xxxxx");
		
//		String body = "{\"name\":\"usersxx\", \"admin\":\"false\"}";
		System.out.println(js.toString());
		ApiMethod am =cac.get("/nodes/node1");
		int code = am.execute().getReturnCode();
		String xx = am.getResponseBodyAsString();
		System.out.println(code+"  \n"+xx);
	}
}
