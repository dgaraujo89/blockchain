package com.github.diegogomesaraujo.chain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.diegogomesaraujo.chain.exception.InvalidNoobChainException;
import com.github.diegogomesaraujo.chain.exception.NoobChainException;
import com.github.diegogomesaraujo.coin.Transaction;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class NoobChain {
	
	private List<Block> blockList;
	
	private Integer difficulty;
	
	private NoobChain() {
		this.blockList = new ArrayList<>();
	}
	
	private NoobChain(Integer difficulty) {
		this.blockList = new ArrayList<>();
		this.difficulty = difficulty;
	}
	
	public static NoobChain create(Integer difficulty) {
		return new NoobChain(difficulty);
	}
	
	public static NoobChain createFromBlockList(List<Block> blockList) {
		NoobChain instance = new NoobChain();
		instance.blockList = blockList;
		return instance;
	}
	
	public static NoobChain createFromJson(String json) {
		NoobChain instance = new NoobChain();
		instance.blockList = new GsonBuilder()
				.create()
				.fromJson(json, new TypeToken<List<Block>>() {}.getType());
		
		return instance;
	}
	
	public NoobChain addBlock() throws NoobChainException {
		String hash = "0";
		
		if(!blockList.isEmpty()) {
			hash = blockList.get(blockList.size() - 1).getHash();
		}
		
		blockList.add(new Block(hash));
		
		return this;
	}
	
	public NoobChain addTransaction(Transaction transaction) {
		blockList.get(blockList.size() - 1).addTransaction(transaction);
		return this;
	}
	
	public NoobChain mine() throws NoobChainException {
		blockList.get(blockList.size() - 1).mineBlock(difficulty);
		return this;
	}
	
	public Boolean isValid() throws NoobChainException, InvalidNoobChainException {
		Block currentBlock = null;
		Block previousBlock = null;
		
		for(int i = 1; i < this.blockList.size(); i++) {
			currentBlock = this.blockList.get(i);
			previousBlock = this.blockList.get(i - 1);
			
			if(currentBlock.getNonce() == 0 ||
					!currentBlock.getHash().substring(0, difficulty).equals(StringUtils.leftPad("", difficulty, '0'))) {
				throw new InvalidNoobChainException(String.format("The block (%d) wasn't mined", i));
			}
			
			if(!currentBlock.getHash().equals(currentBlock.calculateHash())) {
				throw new InvalidNoobChainException("Current hashes not equals");
			}
			
			if(!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				throw new InvalidNoobChainException("Previous Hashes not equal");
			}
		}
		
		return true;
	}
	
	public String json() {
		return new GsonBuilder()
			.create()
			.toJson(this.blockList);
	}
	
	public String prettyJson() {
		return new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(this.blockList);
	}
	
}
