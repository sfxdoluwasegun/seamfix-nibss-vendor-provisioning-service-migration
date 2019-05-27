/*13 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;

import com.seamfix.kyc.microservices.utilities.entities.base.BaseLongPkEntity;


@Audited
@Entity
@Table(name = "PROVISIONED_VENDORS", uniqueConstraints={@UniqueConstraint(columnNames={"VENDOR_ID","APP_KEY","APP_ID"})})
public class VendorProvision extends BaseLongPkEntity{
	
	@Column(name = "APP_KEY")
	private String appKey;
	
	@Column(name = "APP_ID")
	private String appId;
	
	@Column(name = "APP_NAME")
	private String appName;
	
	@ManyToOne
	@JoinColumn(name="VENDOR_ID")
	private KycDealer vendor;
	
	
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public KycDealer getVendor() {
		return vendor;
	}

	public void setVendor(KycDealer vendor) {
		this.vendor = vendor;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
