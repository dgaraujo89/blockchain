package com.github.diegogomesaraujo.chain.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.github.diegogomesaraujo.chain.exception.NoobChainException;
import com.github.diegogomesaraujo.coin.Transaction;

public class ChainUtils {
	
	private ChainUtils() {}

	public static String applySha256(String input) throws NoobChainException {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes());
			StringBuilder hexString = new StringBuilder();
			
			String hex;
			for(int i = 0; i < hash.length; i++) {
				hex = Integer.toHexString(0xff & hash[i]);
				
				if(hex.length() == 1) {
					hexString.append('0');
				}
				
				hexString.append(hex);
			}
			
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new NoobChainException(e);
		}
	}
	
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	public static byte[] applyECDSASig(PrivateKey privateKey, String data) throws NoobChainException {
		try {
			Signature dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			dsa.update(data.getBytes());
			
			return dsa.sign();
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
			throw new NoobChainException(e);
		}
	}
	
	public static Boolean verifiyECDSASig(PublicKey publicKey, String data, byte[] signature) throws NoobChainException {
		try {
			Signature dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initVerify(publicKey);
			dsa.update(data.getBytes());
			
			return dsa.verify(signature);
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
			throw new NoobChainException(e);
		}
	}
	
	public static String getMerkleRoot(List<Transaction> transactions) throws NoobChainException {
		Integer count = transactions.size();
		
		final List<String> previousTreeLayer = new ArrayList<>();
		transactions.forEach(t -> previousTreeLayer.add(t.getTransactionId()));
		
		List<String> treeLayer = new ArrayList<>();
		while(count > 1) {
			treeLayer = new ArrayList<>();
			
			for(int i = 1; i < previousTreeLayer.size(); i++) {
				treeLayer.add(applySha256(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
			}
			
			count = treeLayer.size();
		}
		
		return treeLayer.size() == 1 ? treeLayer.get(0) : "";
	}
	
}
