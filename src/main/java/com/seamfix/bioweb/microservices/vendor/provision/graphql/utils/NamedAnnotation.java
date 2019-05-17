package com.seamfix.bioweb.microservices.vendor.provision.graphql.utils;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Named;

public class NamedAnnotation extends AnnotationLiteral<Named> implements Named {

	private static final long serialVersionUID = -4484134136933431497L;
	
	private final String annotationName;

     public NamedAnnotation(final String value) {
         this.annotationName = value;
     }

     public String value() {
        return annotationName;
    }
}
