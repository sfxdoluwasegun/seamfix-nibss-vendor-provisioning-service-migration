/*17 Mar 2018
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.repositories;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Session;

@ApplicationScoped
@Named("persistenceHelper")
public class PersistenceHelper {

	@PersistenceContext(unitName = "vendorProvisionService", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Session getSession() {
		return this.entityManager.unwrap(Session.class);
	}

}
