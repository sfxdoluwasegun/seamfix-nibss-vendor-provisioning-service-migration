package com.seamfix.bioweb.microservices.vendor.provision.exception;

public class LicenseRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1284358809218077572L;

	public LicenseRuntimeException() {
		super();
	}

	public LicenseRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LicenseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public LicenseRuntimeException(String message) {
		super(message);
	}

	public LicenseRuntimeException(Throwable cause) {
		super(cause);
	}

}
