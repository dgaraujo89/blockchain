package com.github.diegogomesaraujo.coin;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.github.diegogomesaraujo.chain.exception.NoobChainException;
import com.github.diegogomesaraujo.chain.util.ChainUtils;
import com.github.diegogomesaraujo.coin.exception.NoobCoinException;

import lombok.Getter;

public class Transaction {

	@Getter
	private String transactionId;
	
	@Getter
	private PublicKey sender;
	
	@Getter
	private PublicKey reciepient;

	@Getter
	private BigDecimal value;

	@Getter
	private byte[] signature;

	@Getter
	private List<TransactionInput> inputs;

	@Getter
	private List<TransactionOutput> outputs;
	
	private static int sequence = 0;

	private Transaction(PublicKey sender, PublicKey reciepient, BigDecimal value, List<TransactionInput> inputs) {
		this.sender = sender;
		this.reciepient = reciepient;
		this.value = value;
		this.inputs = inputs;
		
		outputs = new ArrayList<>();
	}
	
	public static Transaction create(PublicKey sender, PublicKey reciepient, BigDecimal value, List<TransactionInput> inputs) {
		return new Transaction(sender, reciepient, value, inputs);
	}
	
	public String calculateHash() throws NoobChainException {
		sequence++;
		
		return ChainUtils.applySha256(String.format("%s%s%s%d", 
				ChainUtils.getStringFromKey(sender),
				ChainUtils.getStringFromKey(reciepient),
				value.toString(),
				sequence));
	}
	
	public void generateSignature(PrivateKey privateKey) throws NoobChainException {
		String data = ChainUtils.getStringFromKey(sender) +
				ChainUtils.getStringFromKey(reciepient) +
				value.toString();
		
		signature = ChainUtils.applyECDSASig(privateKey, data);
	}
	
	public boolean verifiySignature() throws NoobChainException {
		String data = ChainUtils.getStringFromKey(sender) +
				ChainUtils.getStringFromKey(reciepient) +
				value.toString();
		
		return ChainUtils.verifiyECDSASig(sender, data, signature);
	}
	
	public Boolean processTransaction() throws NoobChainException, NoobCoinException {
		if(!verifiySignature()) {
			throw new NoobCoinException("Transaction Signature failed to verify");
		}
		
		for(TransactionInput input : inputs) {
			//input.setUTXO(UTXO);
		}
		
		return true;
	}
	
}
