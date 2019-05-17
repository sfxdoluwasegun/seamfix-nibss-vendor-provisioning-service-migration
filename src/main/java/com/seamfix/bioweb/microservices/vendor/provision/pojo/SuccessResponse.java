package com.seamfix.bioweb.microservices.vendor.provision.pojo;

import java.io.Serializable;

/**
 * @author DAWUZI
 *
 */

public class SuccessResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean successful;
	private T info;

	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public T getInfo() {
		return info;
	}
	public void setInfo(T info) {
		this.info = info;
	}
}
