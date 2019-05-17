package com.seamfix.bioweb.microservices.vendor.provision.graphql;

import java.util.Optional;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coxautodev.graphql.tools.SchemaParser;
import com.seamfix.bioweb.microservices.vendor.provision.constants.StringConstants;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.mutations.Mutation;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.pojos.GraphQLUser;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.queries.Query;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.utils.NamedAnnotation;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.utils.TokenManager;

import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = StringConstants.GRAPH_QL_SERVLET_PATH)
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	private static final long serialVersionUID = 4759897497216751068L;

	@Inject
	TokenManager tokenManager;

	@Inject
	@Named("mutation")
	Mutation mutation;

	@Inject
	@Named("query")
	Query query;

	public GraphQLEndpoint() {
		super(buildSchema());
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
				.file("schema.graphqls")
				.resolvers(CDI.current().select(Query.class, new NamedAnnotation("query")).get(), 															
						CDI.current().select(Mutation.class, new NamedAnnotation("mutation")).get())
				.build()
				.makeExecutableSchema();
	}
	
	@Override
	protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
		//Validate Token Here

		String email = request.map(req -> req.getHeader("Authorization"))
				.filter(id -> !id.isEmpty())
				.map(id -> id.replace("Bearer ", ""))
				.map(tokenManager::getEmailFromToken)
				.orElse(null);
		
		String localeStr = request.map(req -> req.getHeader("locale"))
    			.filter(locale -> !locale.isEmpty())
    			.orElse(null);

		GraphQLUser user = new GraphQLUser("",email,"");
		user.setRequestHeaders(tokenManager.retrieveHeaderValues(request.get()));
    	user.setLocaleStr(localeStr);

		return new AuthContext(user, request, response);
	}

}
