package com.github.diegogomesaraujo.chain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.diegogomesaraujo.chain.NoobChain;
import com.github.diegogomesaraujo.chain.exception.InvalidNoobChainException;
import com.github.diegogomesaraujo.chain.exception.NoobChainException;
import com.github.diegogomesaraujo.coin.Transaction;

public class NoobChainTest {
	
	private Integer difficulty = 5;

	@Test
	public void testIsValid() throws NoobChainException, InvalidNoobChainException {
		
		NoobChain chain = NoobChain.create(difficulty)
				.addBlock()
					.addTransaction(null)
					.mine()
				.addBlock().mine()
				.addBlock().mine();
		
		System.out.println(chain.prettyJson());
		
		assertEquals(true, chain.isValid());
	}

}
