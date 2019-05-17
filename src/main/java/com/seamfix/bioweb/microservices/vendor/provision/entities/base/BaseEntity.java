package com.seamfix.bioweb.microservices.vendor.provision.entities.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.slf4j.LoggerFactory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author dawuzi
 *
 */

@Getter
@Setter
@EqualsAndHashCode(of="id")
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 7229631406248028347L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false, insertable = true, updatable = false)
	private Long id;

	protected BaseEntity() {
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(getClass().getName())
		.append(getClass().getSimpleName()).append(" {")
		.append(newLine);

		Field[] fields = getClass().getDeclaredFields();

		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				
				String name = field.getName();
				
				result.append("  ")
				.append(name)
				.append(": ");
				
				try {
					
					String prefix = "get";
					
					if (field.getType().isAssignableFrom(Boolean.class)) {
						prefix = "is";
					}
					
					name = name.substring(0, 1).toUpperCase() + name.substring(1);

					result.append(getClass().getMethod(prefix + name).invoke(this));
					
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
					LoggerFactory.getLogger(getClass()).warn("Error reading the property {} from entity : {}", name, getClass().getName(), ex);
				}
				
				result.append(newLine);
			}
		}
		
		result.append('}');

		return result.toString();
	}

}
