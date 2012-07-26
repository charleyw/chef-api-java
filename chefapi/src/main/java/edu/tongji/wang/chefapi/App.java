package edu.tongji.wang.chefapi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.encoders.Base64;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		String path = "/nodes";
		String hashedPath = sha1AndBase64(path);
		System.out.println(hashedPath);

		String body = "";
		String hashedBody = sha1AndBase64(body);

		String userId = "wang";

		String method = "GET";

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateStamp = sdf.format(date);
		dateStamp = dateStamp.replace(" ", "T");
		dateStamp = dateStamp + "Z";
		System.out.println(dateStamp);

		StringBuilder sb = new StringBuilder();
		sb.append("Method:");
		sb.append(method);
		sb.append("\n");
		sb.append("Hashed Path:");
		sb.append(hashedPath);
		sb.append("\n");
		sb.append("X-Ops-Content-Hash:");
		sb.append(hashedBody);
		sb.append("\n");
		sb.append("X-Ops-Timestamp:");
		sb.append(dateStamp);
		sb.append("\n");
		sb.append("X-Ops-UserId:");
		sb.append(userId);

		String auth_header = signWithRSA(sb.toString());

		String[] headers = splitAs60(auth_header);
		for (String s : headers) {
			System.out.println(s);
			System.out.println(s.length());
		}

		GetMethod get = new GetMethod("http://10.60.0.150:4000/nodes");

		get.addRequestHeader("X-Ops-Timestamp", dateStamp);
		get.addRequestHeader("X-Ops-Userid", userId);
		get.addRequestHeader("X-Chef-Version", "0.10.4");
		get.addRequestHeader("Accept", "application/json");
		get.addRequestHeader("X-Ops-Content-Hash", hashedBody);
		get.addRequestHeader("X-Ops-Sign", "version=1.0");


		for (int i = 0; i < headers.length; i++) {
			get.addRequestHeader("X-Ops-Authorization-" + (i + 1), headers[i]);
		}

		HttpClient hc = new HttpClient();
		try {
			int code = hc.executeMethod(get);
			String response = get.getResponseBodyAsString();
			System.out.println(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String sha1AndBase64(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		byte[] outbty = null;
		try {
			md = MessageDigest.getInstance("SHA-1"); // 选择SHA-1，也可以选择MD5
			byte[] digest = md.digest(inStr.getBytes()); // 返回的是byet[]，要转化为String存储比较方便

			outbty = Base64.encode(digest);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return new String(outbty);
	}

	public static String signWithRSA(String inStr) {
		byte[] outStr = null;
		String keyPath = "d:/wang.pem";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(keyPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Security.addProvider(new BouncyCastleProvider());
		try {
			KeyPair kp = (KeyPair) new PEMReader(br).readObject();
			PrivateKey privateKey = kp.getPrivate();
			Signature instance = Signature.getInstance("RSA");
			instance.initSign(privateKey);
			instance.update(inStr.getBytes());

			byte[] signature = instance.sign();
			outStr = Base64.encode(signature);
			String tmp = new String(outStr);
			System.out.println(tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(outStr);
	}

	public static String[] splitAs60(String inStr) {
		int count = inStr.length() / 60;
		String[] out = new String[count + 1];

		for (int i = 0; i < count; i++) {
			String tmp = inStr.substring(i * 60, i * 60 + 60);
			out[i] = tmp;
		}
		if (inStr.length() > count * 60) {
			String tmp = inStr.substring(count * 60, inStr.length());
			out[count] = tmp;
		}
		return out;
	}

	public static String byte2string(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

}
