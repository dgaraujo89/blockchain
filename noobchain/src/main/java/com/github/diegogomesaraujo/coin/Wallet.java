package com.github.diegogomesaraujo.coin;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

import com.github.diegogomesaraujo.coin.exception.NoobCoinException;

import lombok.Getter;

public class Wallet {

	@Getter
	private PrivateKey privateKey;

	@Getter
	private PublicKey publicKey;
	
	public Wallet() throws NoobCoinException {
		generateKeyPair();
	}
	
	protected void generateKeyPair() throws NoobCoinException {
		try {
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("prime192v1");
			
			keyGenerator.initialize(ecGenSpec, secureRandom);
			KeyPair keyPair = keyGenerator.generateKeyPair();
			
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
			throw new NoobCoinException(e);
		}
	}
	
}
