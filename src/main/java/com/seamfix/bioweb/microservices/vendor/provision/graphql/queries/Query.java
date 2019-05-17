/*14 Mar 2018
charles*/
package com.seamfix.bioweb.microservices.vendor.provision.graphql.queries;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coxautodev.graphql.tools.GraphQLRootResolver;


@Dependent
@Named("query")
public class Query implements GraphQLRootResolver {
	
	protected static final Logger logger = LoggerFactory.getLogger(Query.class);
	
	
	
}
