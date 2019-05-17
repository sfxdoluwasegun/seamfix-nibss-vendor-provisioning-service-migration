package com.seamfix.bioweb.microservices.vendor.provision.entities.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Version;

import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author dawuzi
 *
 * @param <T>
 */

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractBasePkEntity<T> implements Serializable, Comparable<AbstractBasePkEntity<T>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7270322627438965338L;

	@Column(name = "ACTIVE", nullable = false, insertable = true, updatable = true)
	private boolean active = true;

	@Column(name = "DELETED", nullable = false, insertable = true, updatable = true)
	private boolean deleted;

	@Column(name = "CREATE_DATE", nullable = false, insertable = true, updatable = false)
	private Date createDate = new Date();

	@Version
	@Column(name = "LAST_MODIFIED", nullable = false, insertable = true, updatable = true)
	private Date lastModified;

	/**
	 * @return the pk
	 */
	public abstract T getPk();
	

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(getTableName())
		.append(" {")
		.append(newLine);

		// determine fields declared in this class only (no fields of
		// superclass)
		Field[] fields = this.getClass().getDeclaredFields();
		// print field names paired with their values
		for (Field field : fields) {
			
			if(Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			
			String name = field.getName();
			
			result.append("  ")
			.append(name)
			.append(": ");

			try {
				// requires access to private field:Strin

				String prefix = "get";
				
				if(field.getType().isAssignableFrom(Boolean.class)){
					prefix = "is";
				}
				
				name = name.substring(0, 1).toUpperCase() + name.substring(1);

				result.append(this.getClass().getMethod(prefix + name).invoke(this));
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				LoggerFactory.getLogger(getClass()).warn("Error reading the property {} from entity : {}", name, getClass().getName(), ex);
			}
			result.append(newLine);
		}
		result.append('}');

		return result.toString();
	}

	/**
	 * @return retrieves the entity table name based on the {@link Table#name()} name value of the {@link Table} annotation if it exists
	 */
	public String getTableName() {
		
		Table table = getClass().getAnnotation(Table.class);
		
		if(table == null) {
			return "";
		}
		
		String tableName = table.name();

		return tableName;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((getPk() == null) ? 0 : getPk().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		AbstractBasePkEntity<T> other = (AbstractBasePkEntity<T>) obj;
		if(getPk() == null || other.getPk() == null) {
			return false;
		}
		return getPk().equals(other.getPk());
	}

	@Override
	public int compareTo(AbstractBasePkEntity<T> other) {
		
		if(this.getPk() == null) {
			if(other.getPk() == null) {
				return 0;
			} else {
				return -1;
			}
		} else {
//			this.getPk() != null
			
			if(other.getPk() == null) {
				return 1;
			} else {
				
				if(!Comparable.class.isAssignableFrom(this.getPk().getClass())) {
					throw new IllegalArgumentException("Class type of the pk does not implement the Comparable interface. "
							+this.getPk().getClass().getName());
				}

				@SuppressWarnings("unchecked")
				Comparable<T> thisComparablePk = (Comparable<T>) this.getPk();
				
				return thisComparablePk.compareTo(other.getPk());
			}
		}
	}
}
