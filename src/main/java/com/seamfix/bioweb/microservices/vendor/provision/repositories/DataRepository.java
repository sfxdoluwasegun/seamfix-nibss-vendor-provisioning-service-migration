/*27 Mar 2018
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.proxy.HibernateProxyHelper;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seamfix.bioweb.microservices.vendor.provision.entities.Setting;
import com.seamfix.bioweb.microservices.vendor.provision.entities.base.AbstractBasePkEntity;
import com.seamfix.bioweb.microservices.vendor.provision.entities.enums.SettingsEnum;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.QueryAlias;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.QueryFetchMode;


@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Dependent
@Named("dataRepository")
public class DataRepository {

	private static final Logger logger = LoggerFactory.getLogger(DataRepository.class);

	private String delimiter = "#s#x#";

	@Inject
	@Named("persistenceHelper")
	PersistenceHelper persistenceHelper;

	public PersistenceHelper getPersistenceHelper() {
		return persistenceHelper;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	

	public boolean isClassMapped(Class<?> clazz) {
		try {
			return persistenceHelper.getSession().getSessionFactory().getClassMetadata(
					HibernateProxyHelper.getClassWithoutInitializingProxy(clazz.newInstance())) != null;
		} catch (InstantiationException e) {
			logger.error("Exception: ", e);
		} catch (IllegalAccessException e) {
			logger.error("Exception: ", e);
		}
		return false;
	}

	public <T> T getByCriteria(Class<T> entityClass, Criterion... criteria) {
		return getByCriteria(entityClass, null, criteria);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getByCriteria(Class<T> entityClass, List<QueryFetchMode> fetchModes, Criterion... criteria) {
		T out = null;
		Session session = persistenceHelper.getSession();
		try {
			Criteria criterions = session.createCriteria(entityClass);
			for (Criterion c : criteria) {
				criterions.add(c);
			}
			
			if(fetchModes != null && !fetchModes.isEmpty()) {
				for(QueryFetchMode fetch : fetchModes) {
					criterions.setFetchMode(fetch.getAlias(), fetch.getFetchMode());
				}
				
			}
			out = (T) criterions.uniqueResult();

		} catch (HibernateException e) {
			logger.error("HibernateException: ", e);
		}
		return out;
	}

	public Serializable create(Object obj)
			throws PersistenceException , SecurityException , IllegalStateException {
		Serializable pk = null;
		Session session = persistenceHelper.getSession();
		try {
			setLastModifiedDate(obj);
			pk = session.save(obj);
			session.flush();
		} catch (PersistenceException | SecurityException | IllegalStateException ex) {
			logger.error("An error occurred while creating: ", ex);
		}
		return pk;
	}

	public boolean update(Object obj) throws PersistenceException , SecurityException , IllegalStateException {
		boolean outcome = false;
		Session session = persistenceHelper.getSession();
		try {
			setLastModifiedDate(obj);
			session.update(obj);
			session.flush();
			outcome = true;
		} catch (PersistenceException | SecurityException | IllegalStateException ex) {
			logger.error("An error occurred while updating: ", ex);
		}
		return outcome;
	}

	private <T> void setLastModifiedDate(Object entity) {
		if(entity instanceof AbstractBasePkEntity) {
			((AbstractBasePkEntity<?>) entity).setLastModified(new Date());
		}
	}

	public <T> List<T> getListByCriteria(Class<T> clz, Criterion... criteria) {
		
		return getListByCriteria(clz, null, null, criteria);
	}
	
	public <T> List<T> getListByCriteria(Class<T> clz, Integer index, Integer size, Criterion... criteria) {
		
		 return getListByCriteria(clz, index, size, null, null, null, criteria);
		
	}
	
	public <T> List<T> getListByCriteria(Class<T> clz, List<QueryAlias> aliases, List<QueryFetchMode> fetchModes, List<Projection> projections,  Criterion... criteria) {
		
		return getListByCriteria(clz, null, null, aliases, fetchModes, projections, criteria);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getListByCriteria(Class<T> clz, Integer index, Integer size, List<QueryAlias> aliases, List<QueryFetchMode> fetchModes, List<Projection> projections,  Criterion... criteria) {
		List<T> out = new ArrayList<T>();
		boolean isMapped = isClassMapped(clz);
		Session session = persistenceHelper.getSession();
		try {
			Criteria criterions = session.createCriteria(clz);
			for (Criterion c : criteria) {
				criterions.add(c);
			}
			
			criterions = modifyCriteria(criterions, aliases, fetchModes, projections, index, size);
			
			if (isMapped) {
				out = criterions.list();
			} else {
				out = criterions.setResultTransformer(Transformers.aliasToBean(clz)).list();
			}

		} catch (HibernateException e) {

			throw new HibernateException("HibernateException", e);
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListByCriteria(Class<T> clz, Criteria criteria) {
		List<T> out = new ArrayList<T>();
		boolean isMapped = isClassMapped(clz);

		try {

			if (isMapped) {
				out = criteria.list();
			} else {
				out = criteria.setResultTransformer(Transformers.aliasToBean(clz)).list();
			}

		} catch (HibernateException e) {

			throw new HibernateException("HibernateException", e);
		}
		return out;
	}

	public boolean save(Object item) throws PersistenceException , SecurityException , IllegalStateException {
		return create(item) != null;
	}

	public String getSettingValue(String settingName, String defaultValue)
			throws PersistenceException , SecurityException , IllegalStateException {
		String val = "";
		Setting setting = null;
		try {
			Query query = persistenceHelper.getEntityManager()
					.createQuery("select s from Setting s where s.name = :name");
			query.setParameter("name", settingName);
			setting = (Setting) query.getSingleResult();
		} catch (PersistenceException | SecurityException | IllegalStateException e) {
			logger.error("Exception getting Settings value ", e);
		}

		if (setting != null && setting.getValue() != null) {
			val = setting.getValue().trim();
		} else {
			val = defaultValue;
			setting = new Setting();
			setting.setName(settingName);
			setting.setValue(defaultValue);
			setting.setDescription(settingName);

			try {
				create(setting);
			} catch (PersistenceException | SecurityException | IllegalStateException e) {
				logger.error("An error occurred while creating setting", e);
			}
		}
		return val;
	}

	public String getSettingValue(SettingsEnum settingsEnum)
			throws  PersistenceException,  SecurityException, IllegalStateException {
		String val = "";
		Setting setting = null;
		logger.error("About to get setting name {} and value {}", settingsEnum.getName(), settingsEnum.getValue());
		try {
			Query query = persistenceHelper.getEntityManager()
					.createQuery("select s from Setting s where s.name = :name");
			query.setParameter("name", settingsEnum.getName());
			setting = (Setting) query.getSingleResult();
		} catch (PersistenceException | SecurityException | IllegalStateException e) {
			logger.error("Exception getting Settings value ", e);
		}

		if (setting != null && setting.getValue() != null) {
			val = setting.getValue().trim();
		} else {
			val = settingsEnum.getValue();
			setting = new Setting();
			setting.setName(settingsEnum.getName());
			setting.setValue(settingsEnum.getValue());
			setting.setDescription(settingsEnum.getDescription());

			try {
				create(setting);
			} catch (PersistenceException | SecurityException | IllegalStateException e) {
				logger.error("Exception getting Settings value ", e);
			}
		}

		return val;
	}
	
	/**
	 * Modify a given criteria.
	 *
	 * @param criteria the criteria to be modified
	 * @param qm {@link QueryModifier} reference
	 */
	@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
	private Criteria modifyCriteria(Criteria criteria, List<QueryAlias> queryAliases, List<QueryFetchMode> fetchModes, List<Projection> projections, Integer index, Integer maxResult) {
		if(queryAliases != null && !queryAliases.isEmpty()) {
			for (QueryAlias queryAlias : queryAliases) {
				if ((queryAlias.getJoinType() == null) && (queryAlias.getWithClause() == null)) {
					criteria.createAlias(queryAlias.getAssociationPath(), queryAlias.getAlias());
				}
				else if ((queryAlias.getWithClause() == null) && (queryAlias.getJoinType() != null)) {
					criteria.createAlias(queryAlias.getAssociationPath(), queryAlias.getAlias(), queryAlias.getJoinType());
				}
				else {
					criteria.createAlias(queryAlias.getAssociationPath(), queryAlias.getAlias(), queryAlias.getJoinType(), queryAlias.getWithClause());
				}
			}
		}
			
		if(fetchModes != null && !fetchModes.isEmpty()) {
			for(QueryFetchMode fetchMode: fetchModes){
				criteria.setFetchMode(fetchMode.getAlias(), fetchMode.getFetchMode());
			}
		}
		
		if (index != null) {
			criteria.setFirstResult(index);
		}
		
		if(maxResult != null) {
			criteria.setMaxResults(maxResult);
		}

		if(projections != null && !projections.isEmpty()) {
			ProjectionList projectionList = Projections.projectionList();
			for (Projection projectn : projections) {
				projectionList.add(projectn);
			}
			criteria.setProjection(projectionList);
		}
		return criteria;
	}
}
