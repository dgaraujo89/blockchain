package com.github.diegogomesaraujo.chain.exception;

public class NoobChainException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoobChainException() {
		super();
	}

	public NoobChainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoobChainException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoobChainException(String message) {
		super(message);
	}

	public NoobChainException(Throwable cause) {
		super(cause);
	}

}
