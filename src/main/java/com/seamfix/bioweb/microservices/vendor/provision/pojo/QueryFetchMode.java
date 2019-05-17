package com.seamfix.bioweb.microservices.vendor.provision.pojo;

import org.hibernate.FetchMode;

/**
 * The Class QueryFetchMode.
 */
public class QueryFetchMode {
	/** The alias. */
	private String alias;
	
	/** The fetch mode. */
	private FetchMode fetchMode = FetchMode.DEFAULT;

	public QueryFetchMode() {
		
	}
	
	public QueryFetchMode(String alias, FetchMode fetchMode) {
		this.alias = alias;
		this.fetchMode = fetchMode;
	}

	/**
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias.
	 *
	 * @param alias the new alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Gets the fetch mode.
	 *
	 * @return the fetch mode
	 */
	public FetchMode getFetchMode() {
		return fetchMode;
	}

	/**
	 * Sets the fetch mode.
	 *
	 * @param fetchMode the new fetch mode
	 */
	public void setFetchMode(FetchMode fetchMode) {
		this.fetchMode = fetchMode;
	}
}
