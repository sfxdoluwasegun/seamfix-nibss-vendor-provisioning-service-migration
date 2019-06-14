/*16 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.seamfix.bioweb.microservices.vendor.provision.entities.base.BaseLongPkEntity;

@Audited
@Entity
@Table(name = "KYC_DEALER")
public class KycDealer extends BaseLongPkEntity {
	
	@Column(name = "DEAL_CODE", nullable = true)
    private String dealCode;
	
	@Column(name = "NAME")
    private String name;


	public String getDealCode() {
		return dealCode;
	}

	public void setDealCode(String dealCode) {
		this.dealCode = dealCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
