package com.crypto.utils;

import java.util.Random;

import com.crypto.model.Enum.WalletType;

public class AddressGenerator {

	
    private static final String CHARS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    /**
     * Génére une address pour un type de wallet
     */
  
    private static String randomString(int length) {
    	Random random =new Random();
    	StringBuilder sb =new StringBuilder();
    	
    	for(int i = 0; i<length; i++) {
    		sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
    	}
    	return sb.toString();
    	}
}
