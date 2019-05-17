package com.seamfix.bioweb.microservices.vendor.provision.graphql.client;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coxautodev.graphql.tools.SchemaParser;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.AuthContext;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.mutations.Mutation;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.pojos.GraphQLUser;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.queries.Query;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.utils.NamedAnnotation;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.utils.TokenManager;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;

@Dependent
@Named("graphQLClient")
public class GraphQLClient {
	
	@Inject
	@Named("mutation")
	Mutation mutation;
	
	@Inject
	@Named("query")
	Query query;
	
	@Inject
	TokenManager tokenManager;
	
	private GraphQLSchema graphQLSchema;
	
	
	public GraphQLClient() {
		this.graphQLSchema = buildSchema();
	}
		
	public GraphQLSchema getGraphQLSchema() {
		return graphQLSchema;
	}



	public void setGraphQLSchema(GraphQLSchema graphQLSchema) {
		this.graphQLSchema = graphQLSchema;
	}
	
	
	
	private GraphQLSchema buildSchema() {
    	return SchemaParser.newParser()
        		.file("schema.graphqls")
                .resolvers(CDI.current().select(Query.class, new NamedAnnotation("query")).get(), 															
                		CDI.current().select(Mutation.class, new NamedAnnotation("mutation")).get())
                .build()
                .makeExecutableSchema();
    }
	
	public <K, V> Map<String, Map<K,V>> callGraphQLMethod(String graphQLString, AuthContext context) {
		//GraphQLSchema graphQLSchema = buildSchema();
		GraphQL build = GraphQL.newGraphQL(getGraphQLSchema()).build();
		ExecutionResult executionResult = build.execute(graphQLString,context);
		Map<String, Map<K,V>> result = executionResult.getData();
		return result;
		
	}
	
	public GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
    	//Validate Token Here
    	    													
    	String email = request.map(req -> req.getHeader("Authorization"))
    			.filter(token -> !token.isEmpty())
    			.map(token -> token.replace("Bearer ", ""))
    			.map(tokenManager::getEmailFromToken)
    			.orElse(null);
    	
    	if(email == null) {
    		return null;
    	}
    	
    	String localeStr = request.map(req -> req.getHeader("locale"))
    			.filter(locale -> !locale.isEmpty())
    			.orElse(null);
    	
    	GraphQLUser user = new GraphQLUser("",email,"");
    	user.setRequestHeaders(tokenManager.retrieveHeaderValues(request.get()));
    	user.setLocaleStr(localeStr);
    	return new AuthContext(user, request, response);
    }
	
}
