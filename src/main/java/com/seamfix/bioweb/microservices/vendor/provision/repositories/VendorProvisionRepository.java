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
					.createQuery("select vp from PROVISIONED_VENDORS vp where vp.vendor.dealCode = :vendorId");
			query.setParameter("vendorId", vendorId);
			vendorProvision = (VendorProvision) query.getSingleResult();
		} catch (PersistenceException | SecurityException | IllegalStateException e) {
			logger.error("Exception getting VendorProvision ", e);
		}
	return vendorProvision;
}
	
	public VendorProvision getValidProvisionedDevice(String vendorId)
				throws PersistenceException , SecurityException , IllegalStateException {
			VendorProvision vendorProvision = null;
			try {
				Query query = persistenceHelper.getEntityManager()
						.createQuery("select vp from PROVISIONED_VENDORS vp where vp.vendor != null and vp.vendor.dealCode = :vendorId and vp.active = :active and vp.vendor.active = :active");
				query.setParameter("vendorId", vendorId);
				query.setParameter("active", true);
				vendorProvision = (VendorProvision) query.getSingleResult();
			} catch (PersistenceException | SecurityException | IllegalStateException e) {
				logger.error("Exception getting Valid VendorProvision ", e);
			}
		return vendorProvision;
	}
	
	public VendorProvision getValidProvisionedDevice(String vendorId, String appKey, String appId)
			throws PersistenceException , SecurityException , IllegalStateException {
		VendorProvision vendorProvision = null;
		logger.debug("vendorId {} ", vendorId);
		logger.debug("appKey {} ", appKey);
		logger.debug("EappId {} ", appId);
		
		logger.error("vendorId {} ", vendorId);
		logger.error("appKey {} ", appKey);
		logger.error("EappId {} ", appId);
		try {
			Query query = persistenceHelper.getEntityManager()
					.createQuery("select vp from PROVISIONED_VENDORS vp where vp.vendor != null and vp.vendor.dealCode = :vendorId and vp.active = :active and vp.vendor.active = :active "
							+ "and vp.appKey = :appKey and vp.appId = :appId");
			query.setParameter("vendorId", vendorId);
			query.setParameter("appKey", appKey);
			query.setParameter("appId", appId);
			query.setParameter("active", true);
			vendorProvision = (VendorProvision) query.getSingleResult();
		} catch (PersistenceException | SecurityException | IllegalStateException e) {
			logger.error("Exception getting Valid VendorProvision ", e);
		}
	return vendorProvision;
}

}
