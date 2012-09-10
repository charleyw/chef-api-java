package edu.tongji.wang.chefapi;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.tongji.wang.chefapi.method.ApiMethod;

public class chefClientTest extends TestCase {
    private static String CHEF_NODE_STR = "{  \"name\": \"latte\",  \"chef_type\": \"node\",  \"json_class\": \"Chef::Node\",  \"attributes\": { \"hardware_type\": \"laptop\" },  \"overrides\": {  },  \"defaults\": {  },  \"run_list\": [ \"recipe[install_pg]\",\"recipe[install_license]\",\"recipe[install_pm]\" ] }";
	@Test
	public void testGet(){
		ChefApiClient cac = new ChefApiClient("wang", "C:/Users/ESVWYZV/wang.pem", "http://macloud.dnsdynamic.com:4000");

		ApiMethod am =cac.get("/nodes/node1");
		int code = am.execute().getReturnCode();
		String xx = am.getResponseBodyAsString();
		System.out.println(code+"  \n"+xx);
		
//		ApiMethod am2 = cac.post("/nodes").body(CHEF_NODE_STR).execute();
		
//		System.out.println(code+"  \n"+am2.getResponseBodyAsString());
		
		cac.delete("/nodes"+"/"+"latte").execute();
	}
}
