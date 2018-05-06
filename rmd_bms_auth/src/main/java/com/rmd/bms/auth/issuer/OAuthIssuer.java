package com.rmd.bms.auth.issuer;

import java.security.MessageDigest;
import java.util.UUID;


/**
 * Token 生成
 * @author Administrator
 *
 */
public class OAuthIssuer {
	public String generateValue(){
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public String generateValue(String param){
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String args[]){
    	System.out.println(UUID.randomUUID().toString());
    	System.out.println(new OAuthIssuer().generateValue());
    }
}