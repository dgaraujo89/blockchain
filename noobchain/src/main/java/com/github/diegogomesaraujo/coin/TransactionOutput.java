package com.github.diegogomesaraujo.coin;

import java.math.BigDecimal;
import java.security.PublicKey;

import com.github.diegogomesaraujo.chain.exception.NoobChainException;
import com.github.diegogomesaraujo.chain.util.ChainUtils;

import lombok.Getter;

public class TransactionOutput {

	@Getter
	private String id;

	@Getter
	private PublicKey reciepient;

	@Getter
	private BigDecimal value;

	@Getter
	private String parentTransactionId;

	public TransactionOutput(PublicKey reciepient, BigDecimal value, String parentTransactionId) throws NoobChainException {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		
		this.id = calculateTransactionOutputId();
	}
	
	public Boolean isMine(PublicKey publicKey) {
		return publicKey == this.reciepient;
	}
	
	private String calculateTransactionOutputId() throws NoobChainException {
		return ChainUtils.applySha256(ChainUtils.getStringFromKey(reciepient) + value.toPlainString() + parentTransactionId);
	}
	
}
