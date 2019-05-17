/*13 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.repositories;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seamfix.bioweb.microservices.vendor.provision.entities.VendorProvision;

@Dependent
@Named("vendorProvisionRepository")
public class VendorProvisionRepository extends DataRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(VendorProvisionRepository.class);
	
	public VendorProvision getProvisionedDevice(String vendorId)
				throws PersistenceException , SecurityException , IllegalStateException {
			VendorProvision vendorProvision = null;
			try {
				Query query = persistenceHelper.getEntityManager()
						.createQuery("select vp from VendorProvision vp where vp.vendor.dealCode = :vendorId");
				query.setParameter("vendorId", vendorId);
				vendorProvision = (VendorProvision) query.getSingleResult();
			} catch (PersistenceException | SecurityException | IllegalStateException e) {
				logger.error("Exception getting VendorProvision value ", e);
			}
		return vendorProvision;
	}

}
