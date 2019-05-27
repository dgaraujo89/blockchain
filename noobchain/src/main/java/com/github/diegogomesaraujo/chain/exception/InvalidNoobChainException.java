package com.github.diegogomesaraujo.chain.exception;

public class InvalidNoobChainException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidNoobChainException(String message) {
		super(message);
	}
	
	public InvalidNoobChainException(String message, Throwable cause) {
		super(message, cause);
	}

}
