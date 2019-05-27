package com.github.diegogomesaraujo.coin;

import lombok.Getter;
import lombok.Setter;

public class TransactionInput {

	@Getter
	private String transactionId;
	
	@Getter @Setter
	private TransactionOutput UTXO;
	
	public TransactionInput(String transactionId) {
		this.transactionId = transactionId;
	}
	
}
