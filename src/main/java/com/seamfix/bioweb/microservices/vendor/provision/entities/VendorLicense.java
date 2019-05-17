/*15 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.seamfix.kyc.microservices.utilities.entities.base.BaseLongPkEntity;

@Audited
@Entity
@Table(name = "VENDOR_LICENSES")
public class VendorLicense extends BaseLongPkEntity{
	
	@Column(name = "VENDOR_ID")
	private Long vendorId;
	
	@Column(name = "NO_OF_LICENSES")
	private int noOfLicenses;
	
	@Column(name = "ALLOCATED_LICENSES")
	private int allocatedLicenses;
	
	public int getNoOfLicenses() {
		return noOfLicenses;
	}

	public void setNoOfLicenses(int noOfLicenses) {
		this.noOfLicenses = noOfLicenses;
	}

	public Long getVendor() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public int getAllocatedLicenses() {
		return allocatedLicenses;
	}

	public void setAllocatedLicenses(int allocatedLicenses) {
		this.allocatedLicenses = allocatedLicenses;
	}


}