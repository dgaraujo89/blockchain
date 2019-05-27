package com.github.diegogomesaraujo.coin;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import com.github.diegogomesaraujo.chain.exception.NoobChainException;
import com.github.diegogomesaraujo.chain.util.ChainUtils;
import com.github.diegogomesaraujo.coin.exception.NoobCoinException;

public class TransactionTest {

	@Test
	public void testVerifiySignature() throws NoobChainException, NoobCoinException {
		Security.addProvider(new BouncyCastleProvider());
		
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();
		
		System.out.printf("Private Key: %s%n", ChainUtils.getStringFromKey(walletA.getPrivateKey()));
		System.out.printf("Public Key: %s%n", ChainUtils.getStringFromKey(walletA.getPublicKey()));
		
		Transaction transaction = Transaction.create(walletA.getPublicKey(), walletB.getPublicKey(), new BigDecimal(5), null);
		transaction.generateSignature(walletA.getPrivateKey());
		
		assertEquals(true, transaction.verifiySignature());
	}

}
