package com.seamfix.bioweb.microservices.vendor.provision;

import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;

//import com.sf.biocapture.entity.security.KMUser;

import java.net.URISyntaxException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.test.api.ArquillianResource;

import com.seamfix.bioweb.microservices.vendor.provision.repositories.DataRepository;

import io.restassured.builder.RequestSpecBuilder;

//@RunWith(Arquillian.class)
public class GraphqlEndpointTest {
	
	
	@ArquillianResource
	private URL url;
	
	@Inject
	@Named("dataRepository")
	DataRepository dataRepository;
	
  
	public void should_not_access_non_existence_graphql() throws URISyntaxException{
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(url.toURI());
		given(requestSpecBuilder.build())
				.when()
				.get("/graphql?query=%7BallLinks%7Burl%7D%7D")
				.then()
				.assertThat()
				.statusCode(404);
	}
	

}
