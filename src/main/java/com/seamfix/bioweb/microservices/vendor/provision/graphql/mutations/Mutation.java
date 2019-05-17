package com.seamfix.bioweb.microservices.vendor.provision.graphql.mutations;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coxautodev.graphql.tools.GraphQLRootResolver;


@Dependent
@Named("mutation")
public class Mutation implements GraphQLRootResolver {
	
	protected final static Logger logger = LoggerFactory.getLogger(Mutation.class);

}
