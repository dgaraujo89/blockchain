package com.github.diegogomesaraujo.chain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.diegogomesaraujo.chain.exception.NoobChainException;
import com.github.diegogomesaraujo.chain.util.ChainUtils;
import com.github.diegogomesaraujo.coin.Transaction;

import lombok.Getter;
import lombok.NonNull;

public class Block {

	@Getter
	private String hash;
	
	@Getter
	private String previousHash;
	
	private String merkleRoot;
	
	private List<Transaction> transactions;
	
	private Long timestamp;
	
	@Getter
	private Integer nonce = 0;

	public Block(String previousHash) throws NoobChainException {
		this.previousHash = previousHash;
		this.timestamp = new Date().getTime();
		this.transactions = new ArrayList<>();
		this.hash = this.calculateHash();
	}
	
	public String calculateHash() throws NoobChainException {
		return ChainUtils.applySha256(previousHash + timestamp + nonce + merkleRoot);
	}
	
	public void mineBlock(Integer difficulty) throws NoobChainException {
		merkleRoot = ChainUtils.getMerkleRoot(transactions);
		
		String target = StringUtils.leftPad("0", difficulty, '0');
		
		while(!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
	}
	
	public Boolean addTransaction(@NonNull Transaction transaction) {
		
		if(!"0".equals(this.previousHash)) {
			// TODO: process transaction
		}
		
		transactions.add(transaction);
		
		return true;
	}
	
}
