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

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.encoders.Base64;

public class Utils {
	private Utils(){}
	
	public static String sha1AndBase64(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		byte[] outbty = null;
		try {
			md = MessageDigest.getInstance("SHA-1"); 
			byte[] digest = md.digest(inStr.getBytes()); 
			outbty = Base64.encode(digest);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return new String(outbty);
	}
	
	public static String signWithRSA(String inStr, String pemPath) {
		byte[] outStr = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pemPath));
		} catch (FileNotFoundException e) {
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
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
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
	
}
