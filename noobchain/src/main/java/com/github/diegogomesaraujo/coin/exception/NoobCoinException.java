package com.github.diegogomesaraujo.coin.exception;

public class NoobCoinException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoobCoinException() {
		super();
	}

	public NoobCoinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoobCoinException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoobCoinException(String message) {
		super(message);
	}

	public NoobCoinException(Throwable cause) {
		super(cause);
	}

}
